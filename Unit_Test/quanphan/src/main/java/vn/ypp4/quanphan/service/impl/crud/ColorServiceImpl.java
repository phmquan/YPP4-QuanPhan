package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Color;
import vn.ypp4.quanphan.service.mapper.row.ColorRowMapper;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final ColorRowMapper colorRowMapper;

    public Color createColor(String colorName, String colorHex, String icon) {
        if (colorName == null || colorName.isBlank()) {
            throw new IllegalArgumentException("Color name cannot be null or empty");
        }
        if (colorHex == null || colorHex.isBlank()) {
            throw new IllegalArgumentException("Color hex value cannot be null or empty");
        }

        // Validate color hex format (e.g., #RRGGBB or #RGB)
        if (!colorHex.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
            throw new IllegalArgumentException("Invalid color hex format. Expected #RRGGBB or #RGB");
        }

        // Check if color with this hex already exists
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Color WHERE LOWER(ColorHex) = LOWER(?)",
                Integer.class,
                colorHex);

        if (count != null && count > 0) {
            throw new IllegalStateException("Color with hex value '" + colorHex + "' already exists");
        }

        jdbcTemplate.update(
                "INSERT INTO Color (ColorName, ColorHex, Icon) VALUES (?, ?, ?)",
                colorName, colorHex, icon);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Color WHERE Id = LAST_INSERT_ID()",
                colorRowMapper);
    }

    public Color getColorById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Color WHERE Id = ?",
                    colorRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Color getColorByHex(String colorHex) {
        if (colorHex == null || colorHex.isBlank()) {
            throw new IllegalArgumentException("Color hex value cannot be null or empty");
        }

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Color WHERE LOWER(ColorHex) = LOWER(?)",
                    colorRowMapper,
                    colorHex);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Color> getAllColors() {
        return jdbcTemplate.query(
                "SELECT * FROM Color ORDER BY Id",
                colorRowMapper);
    }

    public int updateColor(int id, String colorName, String colorHex, String icon) {
        if (colorName != null && colorName.isBlank()) {
            throw new IllegalArgumentException("Color name cannot be empty");
        }
        if (colorHex != null) {
            if (colorHex.isBlank()) {
                throw new IllegalArgumentException("Color hex value cannot be empty");
            }
            // Validate color hex format if provided
            if (!colorHex.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
                throw new IllegalArgumentException("Invalid color hex format. Expected #RRGGBB or #RGB");
            }
        }

        // Get existing color to merge with updates
        Color existingColor = getColorById(id);
        if (existingColor == null) {
            throw new IllegalArgumentException("Color with ID " + id + " not found");
        }

        String finalColorName = colorName != null ? colorName : existingColor.getColorName();
        String finalColorHex = colorHex != null ? colorHex : existingColor.getColorHex();
        String finalIcon = icon != null ? icon : existingColor.getIcon();

        // Check if another color with the same hex exists (case-insensitive)
        if (colorHex != null && !finalColorHex.equalsIgnoreCase(existingColor.getColorHex())) {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM Color WHERE LOWER(ColorHex) = LOWER(?) AND Id != ?",
                    Integer.class,
                    finalColorHex, id);

            if (count != null && count > 0) {
                throw new IllegalStateException("Another color with hex value '" + finalColorHex + "' already exists");
            }
        }

        return jdbcTemplate.update(
                "UPDATE Color SET ColorName = ?, ColorHex = ?, Icon = ? WHERE Id = ?",
                finalColorName, finalColorHex, finalIcon, id);
    }

    public int deleteColor(int id) {
        // First check if the color is being used by any labels
        Integer usageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Label WHERE ColorId = ?",
                Integer.class,
                id);

        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException("Cannot delete color as it is being used by " + usageCount + " label(s)");
        }

        return jdbcTemplate.update(
                "DELETE FROM Color WHERE Id = ?",
                id);
    }
}
