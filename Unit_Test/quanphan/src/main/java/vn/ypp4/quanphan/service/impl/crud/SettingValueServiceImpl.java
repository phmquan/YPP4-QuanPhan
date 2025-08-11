package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.SettingValue;
import vn.ypp4.quanphan.service.mapper.SettingValueRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SettingValueServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SettingValueRowMapper settingValueRowMapper;

    public List<SettingValue> findAll() {
        String sql = "SELECT Id, SettingKeyId, SettingContent, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, OwnerId FROM SettingValue";
        return jdbcTemplate.query(sql, settingValueRowMapper);
    }

    public Optional<SettingValue> findById(int id) {
        String sql = "SELECT Id, SettingKeyId, SettingContent, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, OwnerId FROM SettingValue WHERE Id = ?";
        List<SettingValue> settingValues = jdbcTemplate.query(sql, settingValueRowMapper, id);
        return settingValues.isEmpty() ? Optional.empty() : Optional.of(settingValues.get(0));
    }

    public SettingValue save(SettingValue settingValue) {
        if (settingValue.getId() == 0) {
            return create(settingValue);
        } else {
            return update(settingValue);
        }
    }

    private SettingValue create(SettingValue settingValue) {
        String sql = "INSERT INTO SettingValue (SettingKeyId, SettingContent, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, OwnerId) VALUES (?, ?, ?, ?, ?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();
        settingValue.setCreatedAt(now);
        settingValue.setUpdatedAt(now);

        jdbcTemplate.update(sql,
                settingValue.getSettingKeyId(),
                settingValue.getSettingContent(),
                settingValue.getCreatedAt(),
                settingValue.getCreatedBy(),
                settingValue.getUpdatedAt(),
                settingValue.getUpdatedBy(),
                settingValue.getOwnerId());

        return settingValue;
    }

    private SettingValue update(SettingValue settingValue) {
        String sql = "UPDATE SettingValue SET SettingKeyId = ?, SettingContent = ?, UpdatedAt = ?, UpdatedBy = ?, OwnerId = ? WHERE Id = ?";

        settingValue.setUpdatedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                settingValue.getSettingKeyId(),
                settingValue.getSettingContent(),
                settingValue.getUpdatedAt(),
                settingValue.getUpdatedBy(),
                settingValue.getOwnerId(),
                settingValue.getId());

        return settingValue;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM SettingValue WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<SettingValue> findBySettingKeyId(int settingKeyId) {
        String sql = "SELECT Id, SettingKeyId, SettingContent, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, OwnerId FROM SettingValue WHERE SettingKeyId = ?";
        return jdbcTemplate.query(sql, settingValueRowMapper, settingKeyId);
    }

    public List<SettingValue> findByOwnerId(int ownerId) {
        String sql = "SELECT Id, SettingKeyId, SettingContent, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, OwnerId FROM SettingValue WHERE OwnerId = ?";
        return jdbcTemplate.query(sql, settingValueRowMapper, ownerId);
    }
}
