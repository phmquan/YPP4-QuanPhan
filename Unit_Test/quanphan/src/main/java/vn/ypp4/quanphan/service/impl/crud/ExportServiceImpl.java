package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Export;
import vn.ypp4.quanphan.service.mapper.ExportRowMapper;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final ExportRowMapper exportRowMapper;
    @Transactional
    public Export createExport(int workspaceId, int createdBy, LocalDateTime createdAt, int size) {
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        
        // Check if workspace exists
        Integer workspaceExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Workspace WHERE Id = ?",
            Integer.class,
            workspaceId);
            
        if (workspaceExists == null || workspaceExists == 0) {
            throw new IllegalArgumentException("Workspace with ID " + workspaceId + " not found");
        }
        
        // Check if user exists
        Integer userExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM User WHERE Id = ?",
            Integer.class,
            createdBy);
            
        if (userExists == null || userExists == 0) {
            throw new IllegalArgumentException("User with ID " + createdBy + " not found");
        }
        
        jdbcTemplate.update(
            "INSERT INTO Export (WorkspaceId, CreatedBy, CreatedAt, Size) " +
            "VALUES (?, ?, ?, ?)",
            workspaceId, createdBy, createdAt, size);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Export WHERE Id = LAST_INSERT_ID()",
            exportRowMapper);
    }
    public Export getExportById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM Export WHERE Id = ?",
                exportRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Export> getExportsByWorkspace(int workspaceId) {
        return jdbcTemplate.query(
            "SELECT * FROM Export WHERE WorkspaceId = ? ORDER BY CreatedAt DESC",
            exportRowMapper,
            workspaceId);
    }
    public List<Export> getExportsByUser(int userId) {
        return jdbcTemplate.query(
            "SELECT * FROM Export WHERE CreatedBy = ? ORDER BY CreatedAt DESC",
            exportRowMapper,
            userId);
    }
    public List<Export> getAllExports() {
        return jdbcTemplate.query(
            "SELECT * FROM Export ORDER BY CreatedAt DESC",
            exportRowMapper);
    }
    @Transactional
    public int deleteExport(int id) {
        return jdbcTemplate.update(
            "DELETE FROM Export WHERE Id = ?",
            id);
    }
    public long getTotalExportSizeByWorkspace(int workspaceId) {
        Long totalSize = jdbcTemplate.queryForObject(
            "SELECT COALESCE(SUM(Size), 0) FROM Export WHERE WorkspaceId = ?",
            Long.class,
            workspaceId);
            
        return totalSize != null ? totalSize : 0L;
    }
    public long getTotalExportSizeByUser(int userId) {
        Long totalSize = jdbcTemplate.queryForObject(
            "SELECT COALESCE(SUM(Size), 0) FROM Export WHERE CreatedBy = ?",
            Long.class,
            userId);
            
        return totalSize != null ? totalSize : 0L;
    }
}
