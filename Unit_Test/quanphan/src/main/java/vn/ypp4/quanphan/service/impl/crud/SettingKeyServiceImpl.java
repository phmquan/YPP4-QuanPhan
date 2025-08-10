package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.SettingKey;
import vn.ypp4.quanphan.service.mapper.SettingKeyRowMapper;

@Service
@RequiredArgsConstructor
public class SettingKeyServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final SettingKeyRowMapper settingKeyRowMapper;

    @Transactional
    public SettingKey createSettingKey(String keyName, String settingKeyDescription,
            int ownerTypeId, int defaultValue, boolean isBoolean) {

        validateSettingKey(keyName, settingKeyDescription, ownerTypeId, defaultValue, isBoolean);

        // Check if owner type exists
        Integer ownerTypeExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM OwnerType WHERE Id = ?",
                Integer.class,
                ownerTypeId);

        if (ownerTypeExists == null || ownerTypeExists == 0) {
            throw new IllegalArgumentException("Owner type with ID " + ownerTypeId + " not found");
        }

        // Check if setting key name is unique (case-insensitive)
        Integer nameExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM SettingKey WHERE LOWER(KeyName) = LOWER(?)",
                Integer.class,
                keyName);

        if (nameExists != null && nameExists > 0) {
            throw new IllegalStateException("Setting key with name '" + keyName + "' already exists");
        }

        jdbcTemplate.update(
                "INSERT INTO SettingKey (KeyName, SettingKeyDescription, OwnerTypeId, DefaultValue, IsBoolean) " +
                        "VALUES (?, ?, ?, ?, ?)",
                keyName, settingKeyDescription, ownerTypeId, defaultValue, isBoolean ? 1 : 0);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM SettingKey WHERE Id = LAST_INSERT_ID()",
                settingKeyRowMapper);
    }

    public SettingKey getSettingKeyById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM SettingKey WHERE Id = ?",
                    settingKeyRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public SettingKey getSettingKeyByName(String keyName) {
        if (keyName == null || keyName.isBlank()) {
            throw new IllegalArgumentException("Key name cannot be null or empty");
        }

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM SettingKey WHERE LOWER(KeyName) = LOWER(?)",
                    settingKeyRowMapper,
                    keyName);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<SettingKey> getSettingKeysByOwnerType(int ownerTypeId) {
        // Check if owner type exists
        Integer ownerTypeExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM OwnerType WHERE Id = ?",
                Integer.class,
                ownerTypeId);

        if (ownerTypeExists == null || ownerTypeExists == 0) {
            throw new IllegalArgumentException("Owner type with ID " + ownerTypeId + " not found");
        }

        return jdbcTemplate.query(
                "SELECT * FROM SettingKey WHERE OwnerTypeId = ? ORDER BY KeyName",
                settingKeyRowMapper,
                ownerTypeId);
    }

    public List<SettingKey> getAllSettingKeys() {
        return jdbcTemplate.query(
                "SELECT * FROM SettingKey ORDER BY KeyName",
                settingKeyRowMapper);
    }

    @Transactional
    public int updateSettingKey(int id, String keyName, String settingKeyDescription,
            Integer ownerTypeId, Integer defaultValue, Boolean isBoolean) {

        // Get existing setting key
        SettingKey existingKey = getSettingKeyById(id);
        if (existingKey == null) {
            throw new IllegalArgumentException("Setting key with ID " + id + " not found");
        }

        // Use existing values if not provided in the update
        String finalKeyName = keyName != null ? keyName : existingKey.getKeyName();
        String finalDescription = settingKeyDescription != null ? settingKeyDescription
                : existingKey.getSettingKeyDescription();
        int finalOwnerTypeId = ownerTypeId != null ? ownerTypeId : existingKey.getOwnerTypeId();
        int finalDefaultValue = defaultValue != null ? defaultValue : existingKey.getDefaultValue();
        boolean finalIsBoolean = isBoolean != null ? isBoolean : existingKey.isBoolean();

        // Validate the updated values
        validateSettingKey(finalKeyName, finalDescription, finalOwnerTypeId, finalDefaultValue, finalIsBoolean);

        // Check if owner type exists if it's being updated
        if (ownerTypeId != null && ownerTypeId != existingKey.getOwnerTypeId()) {
            Integer ownerTypeExists = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM OwnerType WHERE Id = ?",
                    Integer.class,
                    ownerTypeId);

            if (ownerTypeExists == null || ownerTypeExists == 0) {
                throw new IllegalArgumentException("Owner type with ID " + ownerTypeId + " not found");
            }
        }

        // Check if setting key name is being changed and is unique (case-insensitive)
        if (keyName != null && !keyName.equalsIgnoreCase(existingKey.getKeyName())) {
            Integer nameExists = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM SettingKey WHERE LOWER(KeyName) = LOWER(?) AND Id != ?",
                    Integer.class,
                    keyName, id);

            if (nameExists != null && nameExists > 0) {
                throw new IllegalStateException("Another setting key with name '" + keyName + "' already exists");
            }
        }

        return jdbcTemplate.update(
                "UPDATE SettingKey SET KeyName = ?, SettingKeyDescription = ?, OwnerTypeId = ?, " +
                        "DefaultValue = ?, IsBoolean = ? WHERE Id = ?",
                finalKeyName, finalDescription, finalOwnerTypeId,
                finalDefaultValue, finalIsBoolean ? 1 : 0, id);
    }

    @Transactional
    public int deleteSettingKey(int id) {
        // Check if setting key exists
        SettingKey existingKey = getSettingKeyById(id);
        if (existingKey == null) {
            throw new IllegalArgumentException("Setting key with ID " + id + " not found");
        }

        // Check if setting key is in use
        if (isSettingKeyInUse(id)) {
            throw new IllegalStateException("Cannot delete setting key as it is being used by one or more settings");
        }

        return jdbcTemplate.update(
                "DELETE FROM SettingKey WHERE Id = ?",
                id);
    }

    public boolean isSettingKeyInUse(int id) {
        // Check if any setting values reference this setting key
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM SettingValue WHERE SettingKeyId = ?",
                Integer.class,
                id);

        return count != null && count > 0;
    }

    public List<SettingKey> getBooleanSettingKeys() {
        return jdbcTemplate.query(
                "SELECT * FROM SettingKey WHERE IsBoolean = 1 ORDER BY KeyName",
                settingKeyRowMapper);
    }

    public List<SettingKey> getNumericSettingKeys() {
        return jdbcTemplate.query(
                "SELECT * FROM SettingKey WHERE IsBoolean = 0 ORDER BY KeyName",
                settingKeyRowMapper);
    }

    private void validateSettingKey(String keyName, String settingKeyDescription,
            int ownerTypeId, int defaultValue, boolean isBoolean) {

        if (keyName == null || keyName.isBlank()) {
            throw new IllegalArgumentException("Key name cannot be null or empty");
        }

        if (keyName.length() > 100) {
            throw new IllegalArgumentException("Key name cannot exceed 100 characters");
        }

        // Ensure key name follows a consistent format (e.g., "setting.name.format")
        if (!keyName.matches("^[a-z]+(\\.[a-z]+)*$")) {
            throw new IllegalArgumentException(
                    "Key name must be in lowercase with dot notation (e.g., 'setting.name.format')");
        }

        if (settingKeyDescription == null || settingKeyDescription.isBlank()) {
            throw new IllegalArgumentException("Setting key description cannot be null or empty");
        }

        if (settingKeyDescription.length() > 500) {
            throw new IllegalArgumentException("Setting key description cannot exceed 500 characters");
        }

        if (ownerTypeId <= 0) {
            throw new IllegalArgumentException("Owner type ID must be a positive number");
        }

        // For boolean settings, default value should be 0 or 1
        if (isBoolean && (defaultValue != 0 && defaultValue != 1)) {
            throw new IllegalArgumentException("Default value for boolean setting must be 0 (false) or 1 (true)");
        }
    }
}
