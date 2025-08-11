package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.PowerUp;
import vn.ypp4.quanphan.service.mapper.row.PowerUpRowMapper;

@Service
@RequiredArgsConstructor
public class PowerUpServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final PowerUpRowMapper powerUpRowMapper;

    @Transactional
    public PowerUp createPowerUp(String powerUpName, String iconUrl, String backgroundUrl,
            String authorName, String powerUpDescription, String emailContact,
            String policyUrl, Boolean isStaffPick, Boolean isIntegration, int categoryId) {

        validatePowerUp(powerUpName, iconUrl, backgroundUrl, authorName, powerUpDescription,
                emailContact, policyUrl, isStaffPick, isIntegration, categoryId);

        // Check if category exists
        Integer categoryExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM PowerUpCategory WHERE Id = ?",
                Integer.class,
                categoryId);

        if (categoryExists == null || categoryExists == 0) {
            throw new IllegalArgumentException("PowerUp category with ID " + categoryId + " not found");
        }

        // Check if power-up name is unique (case-insensitive)
        Integer nameExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM PowerUp WHERE LOWER(PowerUpName) = LOWER(?)",
                Integer.class,
                powerUpName);

        if (nameExists != null && nameExists > 0) {
            throw new IllegalStateException("Power-up with name '" + powerUpName + "' already exists");
        }

        jdbcTemplate.update(
                "INSERT INTO PowerUp (PowerUpName, IconUrl, BackgroundUrl, AuthorName, " +
                        "PowerUpDescription, EmailContact, PolicyUrl, IsStaffPick, IsIntegration, CategoryId) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                powerUpName, iconUrl, backgroundUrl, authorName, powerUpDescription,
                emailContact, policyUrl, isStaffPick, isIntegration, categoryId);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM PowerUp WHERE Id = LAST_INSERT_ID()",
                powerUpRowMapper);
    }

    public PowerUp getPowerUpById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM PowerUp WHERE Id = ?",
                    powerUpRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public PowerUp getPowerUpByName(String powerUpName) {
        if (powerUpName == null || powerUpName.isBlank()) {
            throw new IllegalArgumentException("Power-up name cannot be null or empty");
        }

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM PowerUp WHERE LOWER(PowerUpName) = LOWER(?)",
                    powerUpRowMapper,
                    powerUpName);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<PowerUp> getPowerUpsByCategory(int categoryId) {
        // Check if category exists
        Integer categoryExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM PowerUpCategory WHERE Id = ?",
                Integer.class,
                categoryId);

        if (categoryExists == null || categoryExists == 0) {
            throw new IllegalArgumentException("PowerUp category with ID " + categoryId + " not found");
        }

        return jdbcTemplate.query(
                "SELECT * FROM PowerUp WHERE CategoryId = ? ORDER BY PowerUpName",
                powerUpRowMapper,
                categoryId);
    }

    public List<PowerUp> getStaffPicks() {
        return jdbcTemplate.query(
                "SELECT * FROM PowerUp WHERE IsStaffPick = true ORDER BY PowerUpName",
                powerUpRowMapper);
    }

    public List<PowerUp> getIntegrations() {
        return jdbcTemplate.query(
                "SELECT * FROM PowerUp WHERE IsIntegration = true ORDER BY PowerUpName",
                powerUpRowMapper);
    }

    public List<PowerUp> getAllPowerUps() {
        return jdbcTemplate.query(
                "SELECT * FROM PowerUp ORDER BY PowerUpName",
                powerUpRowMapper);
    }

    @Transactional
    public int updatePowerUp(int id, String powerUpName, String iconUrl, String backgroundUrl,
            String authorName, String powerUpDescription, String emailContact,
            String policyUrl, Boolean isStaffPick, Boolean isIntegration, Integer categoryId) {

        // Get existing power-up
        PowerUp existingPowerUp = getPowerUpById(id);
        if (existingPowerUp == null) {
            throw new IllegalArgumentException("Power-up with ID " + id + " not found");
        }

        // Use existing values if not provided in the update
        String finalPowerUpName = powerUpName != null ? powerUpName : existingPowerUp.getPowerUpName();
        String finalIconUrl = iconUrl != null ? iconUrl : existingPowerUp.getIconUrl();
        String finalBackgroundUrl = backgroundUrl != null ? backgroundUrl : existingPowerUp.getBackgroundUrl();
        String finalAuthorName = authorName != null ? authorName : existingPowerUp.getAuthorName();
        String finalPowerUpDescription = powerUpDescription != null ? powerUpDescription
                : existingPowerUp.getPowerUpDescription();
        String finalEmailContact = emailContact != null ? emailContact : existingPowerUp.getEmailContact();
        String finalPolicyUrl = policyUrl != null ? policyUrl : existingPowerUp.getPolicyUrl();
        Boolean finalIsStaffPick = isStaffPick != null ? isStaffPick : existingPowerUp.getIsStaffPick();
        Boolean finalIsIntegration = isIntegration != null ? isIntegration : existingPowerUp.getIsIntegration();
        int finalCategoryId = categoryId != null ? categoryId : existingPowerUp.getCategoryId();

        // Validate the updated values
        validatePowerUp(finalPowerUpName, finalIconUrl, finalBackgroundUrl, finalAuthorName,
                finalPowerUpDescription, finalEmailContact, finalPolicyUrl,
                finalIsStaffPick, finalIsIntegration, finalCategoryId);

        // Check if category exists if it's being updated
        if (categoryId != null && categoryId != existingPowerUp.getCategoryId()) {
            Integer categoryExists = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM PowerUpCategory WHERE Id = ?",
                    Integer.class,
                    categoryId);

            if (categoryExists == null || categoryExists == 0) {
                throw new IllegalArgumentException("PowerUp category with ID " + categoryId + " not found");
            }
        }

        // Check if power-up name is being changed and is unique (case-insensitive)
        if (powerUpName != null && !powerUpName.equalsIgnoreCase(existingPowerUp.getPowerUpName())) {
            Integer nameExists = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM PowerUp WHERE LOWER(PowerUpName) = LOWER(?) AND Id != ?",
                    Integer.class,
                    powerUpName, id);

            if (nameExists != null && nameExists > 0) {
                throw new IllegalStateException("Another power-up with name '" + powerUpName + "' already exists");
            }
        }

        return jdbcTemplate.update(
                "UPDATE PowerUp SET PowerUpName = ?, IconUrl = ?, BackgroundUrl = ?, " +
                        "AuthorName = ?, PowerUpDescription = ?, EmailContact = ?, PolicyUrl = ?, " +
                        "IsStaffPick = ?, IsIntegration = ?, CategoryId = ? WHERE Id = ?",
                finalPowerUpName, finalIconUrl, finalBackgroundUrl, finalAuthorName,
                finalPowerUpDescription, finalEmailContact, finalPolicyUrl,
                finalIsStaffPick, finalIsIntegration, finalCategoryId, id);
    }

    @Transactional
    public int deletePowerUp(int id) {
        // Check if power-up exists
        PowerUp existingPowerUp = getPowerUpById(id);
        if (existingPowerUp == null) {
            throw new IllegalArgumentException("Power-up with ID " + id + " not found");
        }

        // Check if power-up is in use
        if (isPowerUpInUse(id)) {
            throw new IllegalStateException("Cannot delete power-up as it is being used by one or more boards");
        }

        return jdbcTemplate.update(
                "DELETE FROM PowerUp WHERE Id = ?",
                id);
    }

    public boolean isPowerUpInUse(int id) {
        // Check if any boards are using this power-up
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM BoardPowerUp WHERE PowerUpId = ?",
                Integer.class,
                id);

        return count != null && count > 0;
    }

    public int countPowerUpsByCategory(int categoryId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM PowerUp WHERE CategoryId = ?",
                Integer.class,
                categoryId);

        return count != null ? count : 0;
    }

    private void validatePowerUp(String powerUpName, String iconUrl, String backgroundUrl,
            String authorName, String powerUpDescription, String emailContact,
            String policyUrl, Boolean isStaffPick, Boolean isIntegration, int categoryId) {

        if (powerUpName == null || powerUpName.isBlank()) {
            throw new IllegalArgumentException("Power-up name cannot be null or empty");
        }

        if (powerUpName.length() > 100) {
            throw new IllegalArgumentException("Power-up name cannot exceed 100 characters");
        }

        if (iconUrl != null && iconUrl.length() > 255) {
            throw new IllegalArgumentException("Icon URL cannot exceed 255 characters");
        }

        if (backgroundUrl != null && backgroundUrl.length() > 255) {
            throw new IllegalArgumentException("Background URL cannot exceed 255 characters");
        }

        if (authorName == null || authorName.isBlank()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }

        if (authorName.length() > 100) {
            throw new IllegalArgumentException("Author name cannot exceed 100 characters");
        }

        if (powerUpDescription == null || powerUpDescription.isBlank()) {
            throw new IllegalArgumentException("Power-up description cannot be null or empty");
        }

        if (emailContact != null && !emailContact.isBlank()) {
            if (emailContact.length() > 100) {
                throw new IllegalArgumentException("Email contact cannot exceed 100 characters");
            }

            // Basic email validation
            if (!emailContact.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }

        if (policyUrl != null && policyUrl.length() > 255) {
            throw new IllegalArgumentException("Policy URL cannot exceed 255 characters");
        }

        if (isStaffPick == null) {
            throw new IllegalArgumentException("IsStaffPick cannot be null");
        }

        if (isIntegration == null) {
            throw new IllegalArgumentException("IsIntegration cannot be null");
        }

        if (categoryId <= 0) {
            throw new IllegalArgumentException("Category ID must be a positive number");
        }
    }
}
