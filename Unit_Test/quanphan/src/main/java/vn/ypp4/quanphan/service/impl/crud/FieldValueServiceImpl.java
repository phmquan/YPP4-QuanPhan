package vn.ypp4.quanphan.service.impl.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.FieldValue;
import vn.ypp4.quanphan.service.mapper.FieldValueRowMapper;

@Service
@RequiredArgsConstructor
public class FieldValueServiceImpl {
    private final JdbcTemplate jdbcTemplate;

    private final FieldValueRowMapper fieldValueRowMapper;

    @Transactional
    public FieldValue createFieldValue(int cardId, String fieldValue, int customFieldId) {
        if (fieldValue == null) {
            throw new IllegalArgumentException("Field value cannot be null");
        }

        // Check if card exists
        Integer cardExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Cards WHERE Id = ?",
                Integer.class,
                cardId);

        if (cardExists == null || cardExists == 0) {
            throw new IllegalArgumentException("Card with ID " + cardId + " not found");
        }

        // Check if custom field exists
        Integer customFieldExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CustomField WHERE Id = ?",
                Integer.class,
                customFieldId);

        if (customFieldExists == null || customFieldExists == 0) {
            throw new IllegalArgumentException("Custom field with ID " + customFieldId + " not found");
        }

        // Check if a field value already exists for this card and custom field
        FieldValue existingFieldValue = getFieldValueByCardAndField(cardId, customFieldId);
        if (existingFieldValue != null) {
            throw new IllegalStateException(
                    "A field value already exists for card " + cardId + " and custom field " + customFieldId);
        }

        jdbcTemplate.update(
                "INSERT INTO FieldValue (CardId, FieldValue, CustomFieldId) VALUES (?, ?, ?)",
                cardId, fieldValue, customFieldId);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM FieldValue WHERE Id = LAST_INSERT_ID()",
                fieldValueRowMapper);
    }

    public FieldValue getFieldValueById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM FieldValue WHERE Id = ?",
                    fieldValueRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public FieldValue getFieldValueByCardAndField(int cardId, int customFieldId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM FieldValue WHERE CardId = ? AND CustomFieldId = ?",
                    fieldValueRowMapper,
                    cardId, customFieldId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<FieldValue> getFieldValuesByCard(int cardId) {
        return jdbcTemplate.query(
                "SELECT * FROM FieldValue WHERE CardId = ?",
                fieldValueRowMapper,
                cardId);
    }

    public List<FieldValue> getFieldValuesByCustomField(int customFieldId) {
        return jdbcTemplate.query(
                "SELECT * FROM FieldValue WHERE CustomFieldId = ?",
                fieldValueRowMapper,
                customFieldId);
    }

    public Map<Integer, String> getFieldValuesMapByCard(int cardId) {
        List<FieldValue> fieldValues = getFieldValuesByCard(cardId);
        Map<Integer, String> result = new HashMap<>();

        for (FieldValue fv : fieldValues) {
            result.put(fv.getCustomFieldId(), fv.getFieldValue());
        }

        return result;
    }

    @Transactional
    public int updateFieldValue(int id, String fieldValue) {
        if (fieldValue == null) {
            throw new IllegalArgumentException("Field value cannot be null");
        }

        return jdbcTemplate.update(
                "UPDATE FieldValue SET FieldValue = ? WHERE Id = ?",
                fieldValue, id);
    }

    @Transactional
    public int updateOrCreateFieldValue(int cardId, String fieldValue, int customFieldId) {
        if (fieldValue == null) {
            throw new IllegalArgumentException("Field value cannot be null");
        }

        // Check if card exists
        Integer cardExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Cards WHERE Id = ?",
                Integer.class,
                cardId);

        if (cardExists == null || cardExists == 0) {
            throw new IllegalArgumentException("Card with ID " + cardId + " not found");
        }

        // Check if custom field exists
        Integer customFieldExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CustomField WHERE Id = ?",
                Integer.class,
                customFieldId);

        if (customFieldExists == null || customFieldExists == 0) {
            throw new IllegalArgumentException("Custom field with ID " + customFieldId + " not found");
        }

        // Try to update existing record
        int rowsAffected = jdbcTemplate.update(
                "UPDATE FieldValue SET FieldValue = ? WHERE CardId = ? AND CustomFieldId = ?",
                fieldValue, cardId, customFieldId);

        // If no record was updated, insert a new one
        if (rowsAffected == 0) {
            jdbcTemplate.update(
                    "INSERT INTO FieldValue (CardId, FieldValue, CustomFieldId) VALUES (?, ?, ?)",
                    cardId, fieldValue, customFieldId);
            return 1; // Return 1 to indicate one record was affected
        }

        return rowsAffected;
    }

    @Transactional
    public int deleteFieldValue(int id) {
        return jdbcTemplate.update(
                "DELETE FROM FieldValue WHERE Id = ?",
                id);
    }

    @Transactional
    public int deleteFieldValuesByCard(int cardId) {
        return jdbcTemplate.update(
                "DELETE FROM FieldValue WHERE CardId = ?",
                cardId);
    }

    @Transactional
    public int deleteFieldValuesByCustomField(int customFieldId) {
        return jdbcTemplate.update(
                "DELETE FROM FieldValue WHERE CustomFieldId = ?",
                customFieldId);
    }
}
