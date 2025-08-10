package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Label;
import vn.ypp4.quanphan.service.mapper.LabelRowMapper;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final LabelRowMapper labelRowMapper;
    @Transactional
    public Label createLabel(String title, int colorId, boolean isDefault, int boardId,
            LocalDateTime createdAt, int createdBy) {
        
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Label title cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        
        // Set updatedAt to createdAt if not provided
        LocalDateTime updatedAt = createdAt;
        int updatedBy = createdBy;
        
        jdbcTemplate.update(
            "INSERT INTO Label (Title, ColorId, IsDefault, BoardId, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
            title, colorId, isDefault, boardId, createdAt, createdBy, updatedAt, updatedBy);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Label WHERE Id = LAST_INSERT_ID()",
            labelRowMapper);
    }
    public Label getLabelById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM Label WHERE Id = ?",
                labelRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Label> getLabelsByBoard(int boardId) {
        return jdbcTemplate.query(
            "SELECT * FROM Label WHERE BoardId = ? ORDER BY Title",
            labelRowMapper,
            boardId);
    }
    public List<Label> getDefaultLabelsByBoard(int boardId) {
        return jdbcTemplate.query(
            "SELECT * FROM Label WHERE BoardId = ? AND IsDefault = true ORDER BY Title",
            labelRowMapper,
            boardId);
    }
    @Transactional
    public int updateLabel(int id, String title, Integer colorId, Boolean isDefault,
            LocalDateTime updatedAt, int updatedBy) {
        
        if (title != null && title.isBlank()) {
            throw new IllegalArgumentException("Label title cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        // Get existing label to merge with updates
        Label existingLabel = getLabelById(id);
        if (existingLabel == null) {
            throw new IllegalArgumentException("Label with ID " + id + " not found");
        }
        
        String finalTitle = title != null ? title : existingLabel.getTitle();
        int finalColorId = colorId != null ? colorId : existingLabel.getColorId();
        boolean finalIsDefault = isDefault != null ? isDefault : existingLabel.isDefault();
        
        return jdbcTemplate.update(
            "UPDATE Label SET Title = ?, ColorId = ?, IsDefault = ?, " +
            "UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
            finalTitle, finalColorId, finalIsDefault, updatedAt, updatedBy, id);
    }
    @Transactional
    public int updateLabelColor(int id, int colorId, LocalDateTime updatedAt, int updatedBy) {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        return jdbcTemplate.update(
            "UPDATE Label SET ColorId = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
            colorId, updatedAt, updatedBy, id);
    }
    @Transactional
    public int deleteLabel(int id) {
        // First delete all CardLabel associations for this label
        jdbcTemplate.update(
            "DELETE FROM CardLabel WHERE LabelId = ?",
            id);
            
        // Then delete the label itself
        return jdbcTemplate.update(
            "DELETE FROM Label WHERE Id = ?",
            id);
    }
    @Transactional
    public int deleteLabelsByBoard(int boardId) {
        // First delete all CardLabel associations for labels in this board
        jdbcTemplate.update(
            "DELETE cl FROM CardLabel cl " +
            "INNER JOIN Label l ON cl.LabelId = l.Id " +
            "WHERE l.BoardId = ?",
            boardId);
            
        // Then delete all labels for this board
        return jdbcTemplate.update(
            "DELETE FROM Label WHERE BoardId = ?",
            boardId);
    }
}
