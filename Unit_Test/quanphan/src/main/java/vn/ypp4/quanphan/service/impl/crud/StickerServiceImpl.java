package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Sticker;
import vn.ypp4.quanphan.service.mapper.row.StickerRowMapper;

@Service
@RequiredArgsConstructor
public class StickerServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final StickerRowMapper stickerRowMapper;

    @Transactional
    public Sticker createSticker(int categoryId, String stickerName, String stickerUrl,
            LocalDateTime createdAt, int createdBy) {

        if (stickerName == null || stickerName.isBlank()) {
            throw new IllegalArgumentException("Sticker name cannot be null or empty");
        }
        if (stickerUrl == null || stickerUrl.isBlank()) {
            throw new IllegalArgumentException("Sticker URL cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        // Set updatedAt to createdAt if not provided
        LocalDateTime updatedAt = createdAt;
        int updatedBy = createdBy;

        // Check if sticker with this URL already exists
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Sticker WHERE LOWER(StickerUrl) = LOWER(?)",
                Integer.class,
                stickerUrl);

        if (count != null && count > 0) {
            throw new IllegalStateException("Sticker with URL '" + stickerUrl + "' already exists");
        }

        jdbcTemplate.update(
                "INSERT INTO Sticker (CategoryId, StickerName, StickerUrl, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                categoryId, stickerName, stickerUrl, createdAt, createdBy, updatedAt, updatedBy);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Sticker WHERE Id = LAST_INSERT_ID()",
                stickerRowMapper);
    }

    public Sticker getStickerById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Sticker WHERE Id = ?",
                    stickerRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Sticker> getStickersByCategory(int categoryId) {
        return jdbcTemplate.query(
                "SELECT * FROM Sticker WHERE CategoryId = ? ORDER BY StickerName",
                stickerRowMapper,
                categoryId);
    }

    public List<Sticker> getAllStickers() {
        return jdbcTemplate.query(
                "SELECT * FROM Sticker ORDER BY CategoryId, StickerName",
                stickerRowMapper);
    }

    @Transactional
    public int updateSticker(int id, Integer categoryId, String stickerName, String stickerUrl,
            LocalDateTime updatedAt, int updatedBy) {

        if (stickerName != null && stickerName.isBlank()) {
            throw new IllegalArgumentException("Sticker name cannot be empty");
        }
        if (stickerUrl != null && stickerUrl.isBlank()) {
            throw new IllegalArgumentException("Sticker URL cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }

        // Get existing sticker to merge with updates
        Sticker existingSticker = getStickerById(id);
        if (existingSticker == null) {
            throw new IllegalArgumentException("Sticker with ID " + id + " not found");
        }

        int finalCategoryId = categoryId != null ? categoryId : existingSticker.getCategoryId();
        String finalStickerName = stickerName != null ? stickerName : existingSticker.getStickerName();
        String finalStickerUrl = stickerUrl != null ? stickerUrl : existingSticker.getStickerUrl();

        // Check if another sticker with this URL exists (case-insensitive)
        if (stickerUrl != null && !finalStickerUrl.equalsIgnoreCase(existingSticker.getStickerUrl())) {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM Sticker WHERE LOWER(StickerUrl) = LOWER(?) AND Id != ?",
                    Integer.class,
                    finalStickerUrl, id);

            if (count != null && count > 0) {
                throw new IllegalStateException("Another sticker with URL '" + finalStickerUrl + "' already exists");
            }
        }

        return jdbcTemplate.update(
                "UPDATE Sticker SET CategoryId = ?, StickerName = ?, StickerUrl = ?, " +
                        "UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
                finalCategoryId, finalStickerName, finalStickerUrl, updatedAt, updatedBy, id);
    }

    @Transactional
    public int deleteSticker(int id) {
        // First check if the sticker is being used by any card stickers
        Integer usageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CardSticker WHERE StickerId = ?",
                Integer.class,
                id);

        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException("Cannot delete sticker as it is being used by " + usageCount + " card(s)");
        }

        return jdbcTemplate.update(
                "DELETE FROM Sticker WHERE Id = ?",
                id);
    }

    public List<Sticker> searchStickers(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return getAllStickers();
        }

        String searchPattern = "%" + searchTerm.toLowerCase() + "%";
        return jdbcTemplate.query(
                "SELECT * FROM Sticker WHERE LOWER(StickerName) LIKE ? ORDER BY StickerName",
                stickerRowMapper,
                searchPattern);
    }
}
