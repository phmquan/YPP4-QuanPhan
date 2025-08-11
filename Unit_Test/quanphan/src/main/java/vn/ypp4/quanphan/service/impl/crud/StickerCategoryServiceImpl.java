package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.StickerCategory;
import vn.ypp4.quanphan.service.mapper.row.StickerCategoryRowMapper;

@Service
@RequiredArgsConstructor
public class StickerCategoryServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final StickerCategoryRowMapper stickerCategoryRowMapper;

    public StickerCategory createStickerCategory(String categoryValue, String displayValue) {
        if (categoryValue == null || categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be null or empty");
        }
        if (displayValue == null || displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be null or empty");
        }

        // Check if category with this value already exists
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM StickerCategory WHERE LOWER(CategoryValue) = LOWER(?)",
                Integer.class,
                categoryValue);

        if (count != null && count > 0) {
            throw new IllegalStateException("Sticker category with value '" + categoryValue + "' already exists");
        }

        jdbcTemplate.update(
                "INSERT INTO StickerCategory (CategoryValue, DisplayValue) VALUES (?, ?)",
                categoryValue, displayValue);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM StickerCategory WHERE Id = LAST_INSERT_ID()",
                stickerCategoryRowMapper);
    }

    public StickerCategory getStickerCategoryById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM StickerCategory WHERE Id = ?",
                    stickerCategoryRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public StickerCategory getStickerCategoryByValue(String categoryValue) {
        if (categoryValue == null || categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be null or empty");
        }

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM StickerCategory WHERE LOWER(CategoryValue) = LOWER(?)",
                    stickerCategoryRowMapper,
                    categoryValue);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<StickerCategory> getAllStickerCategories() {
        return jdbcTemplate.query(
                "SELECT * FROM StickerCategory ORDER BY DisplayValue",
                stickerCategoryRowMapper);
    }

    public int updateStickerCategory(int id, String categoryValue, String displayValue) {
        if (categoryValue != null && categoryValue.isBlank()) {
            throw new IllegalArgumentException("Category value cannot be empty");
        }
        if (displayValue != null && displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be empty");
        }

        // Get existing category to merge with updates
        StickerCategory existingCategory = getStickerCategoryById(id);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Sticker category with ID " + id + " not found");
        }

        String finalCategoryValue = categoryValue != null ? categoryValue : existingCategory.getCategoryValue();
        String finalDisplayValue = displayValue != null ? displayValue : existingCategory.getDisplayValue();

        // Check if another category with this value exists (case-insensitive)
        if (categoryValue != null && !finalCategoryValue.equalsIgnoreCase(existingCategory.getCategoryValue())) {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM StickerCategory WHERE LOWER(CategoryValue) = LOWER(?) AND Id != ?",
                    Integer.class,
                    finalCategoryValue, id);

            if (count != null && count > 0) {
                throw new IllegalStateException(
                        "Another sticker category with value '" + finalCategoryValue + "' already exists");
            }
        }

        return jdbcTemplate.update(
                "UPDATE StickerCategory SET CategoryValue = ?, DisplayValue = ? WHERE Id = ?",
                finalCategoryValue, finalDisplayValue, id);
    }

    public int deleteStickerCategory(int id) {
        // First check if the category is being used by any stickers
        Integer usageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Sticker WHERE CategoryId = ?",
                Integer.class,
                id);

        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException(
                    "Cannot delete sticker category as it contains " + usageCount + " sticker(s)");
        }

        return jdbcTemplate.update(
                "DELETE FROM StickerCategory WHERE Id = ?",
                id);
    }
}
