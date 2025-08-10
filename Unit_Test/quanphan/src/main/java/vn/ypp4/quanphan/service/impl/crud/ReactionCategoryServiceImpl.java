package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.ReactionCategory;
import vn.ypp4.quanphan.service.mapper.ReactionCategoryRowMapper;

@Service
@RequiredArgsConstructor
public class ReactionCategoryServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final ReactionCategoryRowMapper reactionCategoryRowMapper;
    public ReactionCategory createReactionCategory(String categoryValue, String displayValue) {
        if (categoryValue == null || categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be null or empty");
        }
        if (displayValue == null || displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be null or empty");
        }
        
        // Check if category with this value already exists
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM ReactionCategory WHERE LOWER(CategoryValue) = LOWER(?)",
            Integer.class,
            categoryValue);
            
        if (count != null && count > 0) {
            throw new IllegalStateException("Reaction category with value '" + categoryValue + "' already exists");
        }
        
        jdbcTemplate.update(
            "INSERT INTO ReactionCategory (CategoryValue, DisplayValue) VALUES (?, ?)",
            categoryValue, displayValue);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM ReactionCategory WHERE Id = LAST_INSERT_ID()",
            reactionCategoryRowMapper);
    }
    public ReactionCategory getReactionCategoryById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM ReactionCategory WHERE Id = ?",
                reactionCategoryRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public ReactionCategory getReactionCategoryByValue(String categoryValue) {
        if (categoryValue == null || categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be null or empty");
        }
        
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM ReactionCategory WHERE LOWER(CategoryValue) = LOWER(?)",
                reactionCategoryRowMapper,
                categoryValue);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<ReactionCategory> getAllReactionCategories() {
        return jdbcTemplate.query(
            "SELECT * FROM ReactionCategory ORDER BY Id",
            reactionCategoryRowMapper);
    }
    public int updateReactionCategory(int id, String categoryValue, String displayValue) {
        if (categoryValue != null && categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be empty");
        }
        if (displayValue != null && displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be empty");
        }
        
        // Get existing category to merge with updates
        ReactionCategory existingCategory = getReactionCategoryById(id);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Reaction category with ID " + id + " not found");
        }
        
        String finalCategoryValue = categoryValue != null ? categoryValue : existingCategory.getCategoryValue();
        String finalDisplayValue = displayValue != null ? displayValue : existingCategory.getDisplayValue();
        
        // Check if another category with this value exists (case-insensitive)
        if (categoryValue != null && !finalCategoryValue.equalsIgnoreCase(existingCategory.getCategoryValue())) {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ReactionCategory WHERE LOWER(CategoryValue) = LOWER(?) AND Id != ?",
                Integer.class,
                finalCategoryValue, id);
                
            if (count != null && count > 0) {
                throw new IllegalStateException("Another reaction category with value '" + finalCategoryValue + "' already exists");
            }
        }
        
        return jdbcTemplate.update(
            "UPDATE ReactionCategory SET CategoryValue = ?, DisplayValue = ? WHERE Id = ?",
            finalCategoryValue, finalDisplayValue, id);
    }
    public int deleteReactionCategory(int id) {
        // First check if the category is being used by any reactions
        Integer usageCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Reaction WHERE CategoryId = ?",
            Integer.class,
            id);
            
        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException("Cannot delete reaction category as it is being used by " + usageCount + " reaction(s)");
        }
        
        return jdbcTemplate.update(
            "DELETE FROM ReactionCategory WHERE Id = ?",
            id);
    }
}
