package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.CustomFieldValue;
import vn.ypp4.quanphan.service.mapper.CustomFieldValueRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomFieldValueServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomFieldValueRowMapper customFieldValueRowMapper;

    public List<CustomFieldValue> findAll() {
        String sql = "SELECT Id, CustomFieldId, CardId, Value, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy FROM CustomFieldValue";
        return jdbcTemplate.query(sql, customFieldValueRowMapper);
    }

    public Optional<CustomFieldValue> findById(int id) {
        String sql = "SELECT Id, CustomFieldId, CardId, Value, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy FROM CustomFieldValue WHERE Id = ?";
        List<CustomFieldValue> customFieldValues = jdbcTemplate.query(sql, customFieldValueRowMapper, id);
        return customFieldValues.isEmpty() ? Optional.empty() : Optional.of(customFieldValues.get(0));
    }

    public CustomFieldValue save(CustomFieldValue customFieldValue) {
        if (customFieldValue.getId() == 0) {
            return create(customFieldValue);
        } else {
            return update(customFieldValue);
        }
    }

    private CustomFieldValue create(CustomFieldValue customFieldValue) {
        String sql = "INSERT INTO CustomFieldValue (CustomFieldId, CardId, Value, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy) VALUES (?, ?, ?, ?, ?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();
        customFieldValue.setCreatedAt(now);
        customFieldValue.setUpdatedAt(now);

        jdbcTemplate.update(sql,
                customFieldValue.getCustomFieldId(),
                customFieldValue.getCardId(),
                customFieldValue.getValue(),
                customFieldValue.getCreatedAt(),
                customFieldValue.getCreatedBy(),
                customFieldValue.getUpdatedAt(),
                customFieldValue.getUpdatedBy());

        return customFieldValue;
    }

    private CustomFieldValue update(CustomFieldValue customFieldValue) {
        String sql = "UPDATE CustomFieldValue SET CustomFieldId = ?, CardId = ?, Value = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?";

        customFieldValue.setUpdatedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                customFieldValue.getCustomFieldId(),
                customFieldValue.getCardId(),
                customFieldValue.getValue(),
                customFieldValue.getUpdatedAt(),
                customFieldValue.getUpdatedBy(),
                customFieldValue.getId());

        return customFieldValue;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM CustomFieldValue WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<CustomFieldValue> findByCustomFieldId(int customFieldId) {
        String sql = "SELECT Id, CustomFieldId, CardId, Value, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy FROM CustomFieldValue WHERE CustomFieldId = ?";
        return jdbcTemplate.query(sql, customFieldValueRowMapper, customFieldId);
    }

    public List<CustomFieldValue> findByCardId(int cardId) {
        String sql = "SELECT Id, CustomFieldId, CardId, Value, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy FROM CustomFieldValue WHERE CardId = ?";
        return jdbcTemplate.query(sql, customFieldValueRowMapper, cardId);
    }
}
