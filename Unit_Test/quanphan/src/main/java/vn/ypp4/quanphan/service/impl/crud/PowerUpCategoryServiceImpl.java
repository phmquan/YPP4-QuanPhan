package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.PowerUpCategory;
import vn.ypp4.quanphan.service.mapper.PowerUpCategoryRowMapper;

@Service
@RequiredArgsConstructor
public class PowerUpCategoryServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final PowerUpCategoryRowMapper powerUpCategoryRowMapper;
    @Transactional
    public PowerUpCategory createCategory(String categoryValue, String displayValue) {
        validateCategory(categoryValue, displayValue);
        
        // Check if category value is unique (case-insensitive)
        Integer valueExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM PowerUpCategory WHERE LOWER(CategoryValue) = LOWER(?)",
            Integer.class,
            categoryValue);
            
        if (valueExists != null && valueExists > 0) {
            throw new IllegalStateException("Category with value '" + categoryValue + "' already exists");
        }
        
        jdbcTemplate.update(
            "INSERT INTO PowerUpCategory (CategoryValue, DisplayValue) VALUES (?, ?)",
            categoryValue, displayValue);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM PowerUpCategory WHERE Id = LAST_INSERT_ID()",
            powerUpCategoryRowMapper);
    }
    public PowerUpCategory getCategoryById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM PowerUpCategory WHERE Id = ?",
                powerUpCategoryRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public PowerUpCategory getCategoryByValue(String categoryValue) {
        if (categoryValue == null || categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be null or empty");
        }
        
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM PowerUpCategory WHERE LOWER(CategoryValue) = LOWER(?)",
                powerUpCategoryRowMapper,
                categoryValue);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<PowerUpCategory> getAllCategories() {
        return jdbcTemplate.query(
            "SELECT * FROM PowerUpCategory ORDER BY Id",
            powerUpCategoryRowMapper);
    }
    @Transactional
    public int updateCategory(int id, String categoryValue, String displayValue) {
        // Get existing category
        PowerUpCategory existingCategory = getCategoryById(id);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
        
        // Use existing values if not provided in the update
        String finalCategoryValue = categoryValue != null ? categoryValue : existingCategory.getCategoryValue();
        String finalDisplayValue = displayValue != null ? displayValue : existingCategory.getDisplayValue();
        
        // Validate the updated values
        validateCategory(finalCategoryValue, finalDisplayValue);
        
        // Check if category value is being changed and is unique (case-insensitive)
        if (categoryValue != null && !categoryValue.equalsIgnoreCase(existingCategory.getCategoryValue())) {
            Integer valueExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM PowerUpCategory WHERE LOWER(CategoryValue) = LOWER(?) AND Id != ?",
                Integer.class,
                categoryValue, id);
                
            if (valueExists != null && valueExists > 0) {
                throw new IllegalStateException("Another category with value '" + categoryValue + "' already exists");
            }
        }
        
        return jdbcTemplate.update(
            "UPDATE PowerUpCategory SET CategoryValue = ?, DisplayValue = ? WHERE Id = ?",
            finalCategoryValue, finalDisplayValue, id);
    }
    @Transactional
    public int deleteCategory(int id) {
        // Check if category exists
        PowerUpCategory existingCategory = getCategoryById(id);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
        
        // Check if category is in use
        if (isCategoryInUse(id)) {
            throw new IllegalStateException("Cannot delete category as it is being used by one or more power-ups");
        }
        
        return jdbcTemplate.update(
            "DELETE FROM PowerUpCategory WHERE Id = ?",
            id);
    }
    public boolean isCategoryInUse(int id) {
        // Check if any power-ups reference this category
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM PowerUp WHERE CategoryId = ?",
            Integer.class,
            id);
            
        return count != null && count > 0;
    }
    public int countPowerUpsInCategory(int categoryId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM PowerUp WHERE CategoryId = ?",
            Integer.class,
            categoryId);
            
        return count != null ? count : 0;
    }
    
    private void validateCategory(String categoryValue, String displayValue) {
        if (categoryValue == null || categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be null or empty");
        }
        
        if (categoryValue.length() > 50) {
            throw new IllegalArgumentException("Category value cannot exceed 50 characters");
        }
        
        if (displayValue == null || displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be null or empty");
        }
        
        if (displayValue.length() > 100) {
            throw new IllegalArgumentException("Display value cannot exceed 100 characters");
        }
    }
}
