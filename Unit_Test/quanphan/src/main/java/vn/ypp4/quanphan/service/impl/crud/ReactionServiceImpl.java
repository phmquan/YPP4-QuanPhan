package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Reaction;
import vn.ypp4.quanphan.service.mapper.row.ReactionRowMapper;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final ReactionRowMapper reactionRowMapper;

    public Reaction createReaction(String reactionsName, String shortCode, int categoryId, String icon) {
        if (reactionsName == null || reactionsName.isBlank()) {
            throw new IllegalArgumentException("Reaction name cannot be null or empty");
        }
        if (shortCode == null || shortCode.isBlank()) {
            throw new IllegalArgumentException("Short code cannot be null or empty");
        }

        // Check if reaction with this short code already exists
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Reaction WHERE LOWER(ShortCode) = LOWER(?)",
                Integer.class,
                shortCode);

        if (count != null && count > 0) {
            throw new IllegalStateException("Reaction with short code '" + shortCode + "' already exists");
        }

        jdbcTemplate.update(
                "INSERT INTO Reaction (ReactionsName, ShortCode, CategoryId, Icon) VALUES (?, ?, ?, ?)",
                reactionsName, shortCode, categoryId, icon);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Reaction WHERE Id = LAST_INSERT_ID()",
                reactionRowMapper);
    }

    public Reaction getReactionById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Reaction WHERE Id = ?",
                    reactionRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Reaction getReactionByShortCode(String shortCode) {
        if (shortCode == null || shortCode.isBlank()) {
            throw new IllegalArgumentException("Short code cannot be null or empty");
        }

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Reaction WHERE LOWER(ShortCode) = LOWER(?)",
                    reactionRowMapper,
                    shortCode);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Reaction> getReactionsByCategory(int categoryId) {
        return jdbcTemplate.query(
                "SELECT * FROM Reaction WHERE CategoryId = ? ORDER BY ReactionsName",
                reactionRowMapper,
                categoryId);
    }

    public List<Reaction> getAllReactions() {
        return jdbcTemplate.query(
                "SELECT * FROM Reaction ORDER BY CategoryId, ReactionsName",
                reactionRowMapper);
    }

    public int updateReaction(int id, String reactionsName, String shortCode, Integer categoryId, String icon) {
        if (reactionsName != null && reactionsName.isBlank()) {
            throw new IllegalArgumentException("Reaction name cannot be empty");
        }
        if (shortCode != null && shortCode.isBlank()) {
            throw new IllegalArgumentException("Short code cannot be empty");
        }

        // Get existing reaction to merge with updates
        Reaction existingReaction = getReactionById(id);
        if (existingReaction == null) {
            throw new IllegalArgumentException("Reaction with ID " + id + " not found");
        }

        String finalReactionsName = reactionsName != null ? reactionsName : existingReaction.getReactionsName();
        String finalShortCode = shortCode != null ? shortCode : existingReaction.getShortCode();
        int finalCategoryId = categoryId != null ? categoryId : existingReaction.getCategoryId();
        String finalIcon = icon != null ? icon : existingReaction.getIcon();

        // Check if another reaction with the same short code exists (case-insensitive)
        if (shortCode != null && !finalShortCode.equalsIgnoreCase(existingReaction.getShortCode())) {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM Reaction WHERE LOWER(ShortCode) = LOWER(?) AND Id != ?",
                    Integer.class,
                    finalShortCode, id);

            if (count != null && count > 0) {
                throw new IllegalStateException(
                        "Another reaction with short code '" + finalShortCode + "' already exists");
            }
        }

        return jdbcTemplate.update(
                "UPDATE Reaction SET ReactionsName = ?, ShortCode = ?, CategoryId = ?, Icon = ? WHERE Id = ?",
                finalReactionsName, finalShortCode, finalCategoryId, finalIcon, id);
    }

    public int deleteReaction(int id) {
        // First check if the reaction is being used by any comment reactions
        Integer usageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CommentReaction WHERE ReactionId = ?",
                Integer.class,
                id);

        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException(
                    "Cannot delete reaction as it is being used by " + usageCount + " comment reaction(s)");
        }

        return jdbcTemplate.update(
                "DELETE FROM Reaction WHERE Id = ?",
                id);
    }
}
