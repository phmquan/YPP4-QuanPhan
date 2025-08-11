package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.service.mapper.row.WorkspaceRowMapper;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final WorkspaceRowMapper workspaceRowMapper;

    public Workspace createWorkspace(String name, String description, int categoryId, LocalDateTime createdAt,
            int createdBy,
            LocalDateTime updatedAt, int updatedBy, String logoUrl) {
        if (name == null || name.isBlank()) {
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
                "INSERT INTO Workspaces (WorkspaceName, WorkspaceDescription, TypeId, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, LogoUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                name, description, categoryId, createdAt, createdBy, createdAt, createdBy, logoUrl);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Workspaces WHERE Id = LAST_INSERT_ID()",
                workspaceRowMapper);
    }

    public Workspace getWorkspaceById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Workspaces WHERE Id = ?",
                workspaceRowMapper,
                id);
    }

    public Workspace getWorkspaceByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Workspaces WHERE WorkspaceName = ?",
                workspaceRowMapper,
                name);
    }

    public List<Workspace> getAllWorkspaces() {
        return jdbcTemplate.query(
                "SELECT * FROM Workspaces",
                workspaceRowMapper);
    }

    public int updateWorkspaceById(int id, String name, String description, int categoryId, LocalDateTime updatedAt,
            int updatedBy, String logoUrl) {
        Workspace currentWorkspace = getWorkspaceById(id);

        String finalName = (name != null && !name.isBlank()) ? name : currentWorkspace.getWorkspaceName();
        String finalDescription = (description != null && !description.isBlank()) ? description
                : currentWorkspace.getWorkspaceDescription();
        int finalTypeId = (categoryId != 0) ? categoryId : currentWorkspace.getTypeId();
        String finalLogoUrl = (logoUrl != null && !logoUrl.isBlank()) ? logoUrl : currentWorkspace.getLogoUrl();

        return jdbcTemplate.update(
                "UPDATE Workspaces SET WorkspaceName = ?, WorkspaceDescription = ?, TypeId = ?, UpdatedAt = ?, UpdatedBy = ?, LogoUrl = ? WHERE Id = ?",
                finalName, finalDescription, finalTypeId, LocalDateTime.now(), updatedBy, finalLogoUrl, id);
    }

    public int deleteWorkspace(int id) {
        return jdbcTemplate.update(
                "DELETE FROM Workspaces WHERE Id = ?",
                id);
    }
}
