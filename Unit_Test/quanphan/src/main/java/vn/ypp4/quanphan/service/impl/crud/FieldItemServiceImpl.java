package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.FieldItem;
import vn.ypp4.quanphan.service.mapper.row.FieldItemRowMapper;

@Service
@RequiredArgsConstructor
public class FieldItemServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final FieldItemRowMapper fieldItemRowMapper;

    @Transactional
    public FieldItem createFieldItem(int colorId, String fieldItemValue, int position, int customFieldId) {
        if (fieldItemValue == null || fieldItemValue.isBlank()) {
            throw new IllegalArgumentException("Field item value cannot be null or empty");
        }

        // Check if color exists
        Integer colorExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Color WHERE Id = ?",
                Integer.class,
                colorId);

        if (colorExists == null || colorExists == 0) {
            throw new IllegalArgumentException("Color with ID " + colorId + " not found");
        }

        // Check if custom field exists
        Integer customFieldExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CustomField WHERE Id = ?",
                Integer.class,
                customFieldId);

        if (customFieldExists == null || customFieldExists == 0) {
            throw new IllegalArgumentException("Custom field with ID " + customFieldId + " not found");
        }

        // Check if position is valid (non-negative)
        if (position < 0) {
            throw new IllegalArgumentException("Position must be a non-negative number");
        }

        // Get the current maximum position for this custom field
        Integer maxPosition = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(Position), -1) FROM FieldItem WHERE CustomFieldId = ?",
                Integer.class,
                customFieldId);

        // If position is greater than max position, set it to max + 1
        if (position > maxPosition + 1) {
            position = maxPosition + 1;
        }

        // Shift positions of items that come after the new position
        if (position <= maxPosition) {
            jdbcTemplate.update(
                    "UPDATE FieldItem SET Position = Position + 1 " +
                            "WHERE CustomFieldId = ? AND Position >= ?",
                    customFieldId, position);
        }

        jdbcTemplate.update(
                "INSERT INTO FieldItem (ColorId, FieldItemValue, Position, CustomFieldId) " +
                        "VALUES (?, ?, ?, ?)",
                colorId, fieldItemValue, position, customFieldId);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM FieldItem WHERE Id = LAST_INSERT_ID()",
                fieldItemRowMapper);
    }

    public FieldItem getFieldItemById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM FieldItem WHERE Id = ?",
                    fieldItemRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<FieldItem> getFieldItemsByCustomField(int customFieldId) {
        return jdbcTemplate.query(
                "SELECT * FROM FieldItem WHERE CustomFieldId = ? ORDER BY Position",
                fieldItemRowMapper,
                customFieldId);
    }

    public List<FieldItem> getAllFieldItems() {
        return jdbcTemplate.query(
                "SELECT * FROM FieldItem ORDER BY CustomFieldId, Position",
                fieldItemRowMapper);
    }

    @Transactional
    public int updateFieldItem(int id, Integer colorId, String fieldItemValue, Integer position) {
        // Get existing field item
        FieldItem existingItem = getFieldItemById(id);
        if (existingItem == null) {
            throw new IllegalArgumentException("Field item with ID " + id + " not found");
        }

        // Validate inputs
        if (fieldItemValue != null && fieldItemValue.isBlank()) {
            throw new IllegalArgumentException("Field item value cannot be empty");
        }

        if (colorId != null) {
            // Check if color exists
            Integer colorExists = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM Color WHERE Id = ?",
                    Integer.class,
                    colorId);

            if (colorExists == null || colorExists == 0) {
                throw new IllegalArgumentException("Color with ID " + colorId + " not found");
            }
        }

        // Apply updates
        int finalColorId = colorId != null ? colorId : existingItem.getColorId();
        String finalFieldItemValue = fieldItemValue != null ? fieldItemValue : existingItem.getFieldItemValue();

        // Handle position update if needed
        if (position != null && position != existingItem.getPosition()) {
            if (position < 0) {
                throw new IllegalArgumentException("Position must be a non-negative number");
            }

            // Get the current maximum position for this custom field
            Integer maxPosition = jdbcTemplate.queryForObject(
                    "SELECT COALESCE(MAX(Position), 0) FROM FieldItem WHERE CustomFieldId = ? AND Id != ?",
                    Integer.class,
                    existingItem.getCustomFieldId(), id);

            // If position is greater than max position, set it to max
            if (position > maxPosition) {
                position = maxPosition;
            }

            // Shift positions
            if (position < existingItem.getPosition()) {
                // Moving up - increment positions in the range [newPos, oldPos-1]
                jdbcTemplate.update(
                        "UPDATE FieldItem SET Position = Position + 1 " +
                                "WHERE CustomFieldId = ? AND Position >= ? AND Position < ? AND Id != ?",
                        existingItem.getCustomFieldId(), position, existingItem.getPosition(), id);
            } else {
                // Moving down - decrement positions in the range [oldPos+1, newPos]
                jdbcTemplate.update(
                        "UPDATE FieldItem SET Position = Position - 1 " +
                                "WHERE CustomFieldId = ? AND Position > ? AND Position <= ? AND Id != ?",
                        existingItem.getCustomFieldId(), existingItem.getPosition(), position, id);
            }
        }

        int finalPosition = position != null ? position : existingItem.getPosition();

        return jdbcTemplate.update(
                "UPDATE FieldItem SET ColorId = ?, FieldItemValue = ?, Position = ? WHERE Id = ?",
                finalColorId, finalFieldItemValue, finalPosition, id);
    }

    @Transactional
    public int updateFieldItemPosition(int id, int newPosition) {
        return updateFieldItem(id, null, null, newPosition);
    }

    @Transactional
    public int deleteFieldItem(int id) {
        // Get the field item to get its position and custom field ID
        FieldItem fieldItem = getFieldItemById(id);
        if (fieldItem == null) {
            throw new IllegalArgumentException("Field item with ID " + id + " not found");
        }

        // Check if this field item is being used in any field values
        Integer usageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM FieldValue WHERE FieldItemId = ?",
                Integer.class,
                id);

        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException(
                    "Cannot delete field item as it is being used by " + usageCount + " field value(s)");
        }

        // Delete the field item
        int rowsAffected = jdbcTemplate.update(
                "DELETE FROM FieldItem WHERE Id = ?",
                id);

        if (rowsAffected > 0) {
            // Update positions of remaining items
            jdbcTemplate.update(
                    "UPDATE FieldItem SET Position = Position - 1 " +
                            "WHERE CustomFieldId = ? AND Position > ?",
                    fieldItem.getCustomFieldId(), fieldItem.getPosition());
        }

        return rowsAffected;
    }

    @Transactional
    public int reorderFieldItems(int customFieldId, int oldPosition, int newPosition) {
        // Validate positions
        if (oldPosition < 0 || newPosition < 0) {
            throw new IllegalArgumentException("Positions must be non-negative");
        }

        // Get the current maximum position for this custom field
        Integer maxPosition = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(Position), 0) FROM FieldItem WHERE CustomFieldId = ?",
                Integer.class,
                customFieldId);

        if (oldPosition > maxPosition || newPosition > maxPosition) {
            throw new IllegalArgumentException("Positions must be less than or equal to " + maxPosition);
        }

        if (oldPosition == newPosition) {
            return 0; // No change needed
        }

        // Update positions
        if (oldPosition < newPosition) {
            // Moving down - decrement positions in the range (oldPos, newPos]
            jdbcTemplate.update(
                    "UPDATE FieldItem SET Position = Position - 1 " +
                            "WHERE CustomFieldId = ? AND Position > ? AND Position <= ?",
                    customFieldId, oldPosition, newPosition);
        } else {
            // Moving up - increment positions in the range [newPos, oldPos)
            jdbcTemplate.update(
                    "UPDATE FieldItem SET Position = Position + 1 " +
                            "WHERE CustomFieldId = ? AND Position >= ? AND Position < ?",
                    customFieldId, newPosition, oldPosition);
        }

        // Update the moved item's position
        return jdbcTemplate.update(
                "UPDATE FieldItem SET Position = ? " +
                        "WHERE CustomFieldId = ? AND Position = ?",
                newPosition, customFieldId,
                oldPosition < newPosition ? newPosition + 1 : newPosition - 1);
    }
}
