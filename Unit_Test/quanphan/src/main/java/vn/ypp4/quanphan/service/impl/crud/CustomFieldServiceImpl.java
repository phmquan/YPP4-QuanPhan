package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.CustomField;
import vn.ypp4.quanphan.service.mapper.row.CustomFieldRowMapper;

@Service
@RequiredArgsConstructor
public class CustomFieldServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CustomFieldRowMapper customFieldRowMapper;

    @Transactional
    public CustomField createCustomField(String title, int dataTypeId, int boardId,
            LocalDateTime createdAt, int createdBy, boolean isFrontCardShowed) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Custom field title cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        // Set updatedAt to createdAt if not provided
        LocalDateTime updatedAt = createdAt;
        int updatedBy = createdBy;

        // Get the next position for the new field
        Integer nextPosition = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(Position), -1) + 1 FROM CustomField WHERE BoardId = ?",
                Integer.class,
                boardId);

        if (nextPosition == null) {
            nextPosition = 0;
        }

        jdbcTemplate.update(
                "INSERT INTO CustomField (Title, DataTypeId, BoardId, CreatedAt, CreatedBy, " +
                        "UpdatedAt, UpdatedBy, Position, IsFrontCardShowed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                title, dataTypeId, boardId, createdAt, createdBy, updatedAt, updatedBy,
                nextPosition, isFrontCardShowed);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM CustomField WHERE Id = LAST_INSERT_ID()",
                customFieldRowMapper);
    }

    public CustomField getCustomFieldById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM CustomField WHERE Id = ?",
                customFieldRowMapper,
                id);
    }

    public List<CustomField> getCustomFieldsByBoard(int boardId) {
        return jdbcTemplate.query(
                "SELECT * FROM CustomField WHERE BoardId = ? ORDER BY Position",
                customFieldRowMapper,
                boardId);
    }

    public List<CustomField> getFrontCardShownFields(int boardId) {
        return jdbcTemplate.query(
                "SELECT * FROM CustomField WHERE BoardId = ? AND IsFrontCardShowed = true ORDER BY Position",
                customFieldRowMapper,
                boardId);
    }

    @Transactional
    public int updateCustomField(int id, String title, Integer dataTypeId, Boolean isFrontCardShowed,
            LocalDateTime updatedAt, int updatedBy) {

        if (title != null && title.isBlank()) {
            throw new IllegalArgumentException("Custom field title cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }

        // Get existing custom field to merge with updates
        CustomField existingField = getCustomFieldById(id);

        String finalTitle = title != null ? title : existingField.getTitle();
        int finalDataTypeId = dataTypeId != null ? dataTypeId : existingField.getDataTypeId();
        boolean finalIsFrontCardShowed = isFrontCardShowed != null ? isFrontCardShowed
                : existingField.isFrontCardShowed();

        return jdbcTemplate.update(
                "UPDATE CustomField SET Title = ?, DataTypeId = ?, IsFrontCardShowed = ?, " +
                        "UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
                finalTitle, finalDataTypeId, finalIsFrontCardShowed, updatedAt, updatedBy, id);
    }

    @Transactional
    public int updateCustomFieldPosition(int id, int newPosition, LocalDateTime updatedAt, int updatedBy) {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }

        return jdbcTemplate.update(
                "UPDATE CustomField SET Position = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
                newPosition, updatedAt, updatedBy, id);
    }

    @Transactional
    public int deleteCustomField(int id) {
        // First delete all custom field values associated with this field
        jdbcTemplate.update(
                "DELETE FROM CustomFieldValue WHERE CustomFieldId = ?",
                id);

        // Then delete the custom field itself
        return jdbcTemplate.update(
                "DELETE FROM CustomField WHERE Id = ?",
                id);
    }
}
