package vn.ypp4.quanphan.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.service.interf.WorkspaceService;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {
    private final JdbcTemplate jdbcTemplate;

    // Consistent mapping with Workspace fields and DB columns
    private final RowMapper<Workspace> workspaceRowMapper = new RowMapper<Workspace>() {
        @Override
        public Workspace mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Workspace(
                    rs.getInt("Id"),
                    rs.getString("WorkspaceName"),
                    rs.getString("WorkspaceDescription"),
                    rs.getInt("CategoryId"),
                    rs.getTimestamp("CreatedAt").toInstant(),
                    rs.getInt("CreatedBy"),
                    rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toInstant() : null,
                    rs.getInt("UpdatedBy"),
                    rs.getString("LogoUrl"));
        }
    };

    @Override
    public Workspace createWorkspace(String name, String description, int categoryId, Instant createdAt, int createdBy,
            Instant updatedAt, int updatedBy, String logoUrl) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null or empty for workspace");
        }
        if (categoryId == 0) {
            throw new IllegalArgumentException("Type cannot be null or empty for workspace");
        }
        if (createdBy == 0) {
            throw new IllegalArgumentException("CreatedBy cannot be null");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        jdbcTemplate.update(
                "INSERT INTO Workspaces (WorkspaceName, WorkspaceDescription, CategoryId, CreatedAt, CreatedBy) VALUES (?, ?, ?, ?, ?)",
                name, description, categoryId, java.sql.Timestamp.from(createdAt), createdBy);
        return getWorkspaceByName(name);
    }

    @Override
    public Workspace getWorkspaceById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Workspaces WHERE Id = ?",
                workspaceRowMapper,
                id);
    }

    @Override
    public Workspace getWorkspaceByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Workspaces WHERE WorkspaceName = ?",
                workspaceRowMapper,
                name);
    }

    @Override
    public List<Workspace> getAllWorkspaces() {
        return jdbcTemplate.query(
                "SELECT * FROM Workspaces",
                workspaceRowMapper);
    }

    @Override
    public int updateWorkspaceById(int id, String name, String description, int categoryId, Instant updatedAt,
            int updatedBy, String logoUrl) {
        Workspace currentWorkspace = getWorkspaceById(id);
        currentWorkspace.setWorkspaceName(name.isBlank() ? currentWorkspace.getWorkspaceName() : name);
        currentWorkspace.setWorkspaceDescription(
                description.isBlank() ? currentWorkspace.getWorkspaceDescription() : description);
        currentWorkspace.setCategoryId(categoryId == 0 ? currentWorkspace.getCategoryId() : categoryId);
        currentWorkspace.setUpdatedAt(Instant.now());
        currentWorkspace.setUpdatedBy(updatedBy);
        currentWorkspace.setLogoUrl(logoUrl.isBlank() ? currentWorkspace.getLogoUrl() : logoUrl);
        return jdbcTemplate.update(
                "UPDATE Workspaces SET WorkspaceName = ?, WorkspaceDescription = ?, CategoryId = ?, UpdatedAt = ?, UpdatedBy = ?,LogoUrl = ?   WHERE Id = ?",
                currentWorkspace.getWorkspaceName(), currentWorkspace.getWorkspaceDescription(),
                currentWorkspace.getCategoryId(), currentWorkspace.getUpdatedAt(), currentWorkspace.getUpdatedBy(),
                currentWorkspace.getLogoUrl(), currentWorkspace.getId());
    }

    @Override
    public int deleteWorkspace(int id) {
        return jdbcTemplate.update(
                "DELETE FROM Workspaces WHERE Id = ?",
                id);
    }
}