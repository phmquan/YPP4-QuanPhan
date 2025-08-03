package vn.ypp4.quanphan.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.util.constant.WorkspaceTypeEnum;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Workspace> workspaceRowMapper = new RowMapper<Workspace>() {
        @Override
        public Workspace mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Workspace(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    WorkspaceTypeEnum.valueOf(rs.getString("type")),
                    rs.getTimestamp("created_at").toInstant(),
                    rs.getInt("created_by"));
        }
    };

    public Workspace createWorkspace(String name, String description, WorkspaceTypeEnum type, Instant createdAt,
            int createdBy) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null or empty for workspace");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null or empty for workspace");
        }
        if (createdBy == 0) {
            throw new IllegalArgumentException("CreatedBy cannot be null");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        jdbcTemplate.update(
                "INSERT INTO Workspaces (name, description, type, created_at, created_by) VALUES (?, ?, ?, ?, ?)",
                name, description, type.name(), java.sql.Timestamp.from(createdAt), createdBy);
        // Assuming 'id' is auto-increment and you want to fetch the created workspace
        return getWorkspaceByName(name);
    }

    public Workspace getWorkspaceById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Workspaces WHERE id = ?",
                workspaceRowMapper,
                id);
    }

    public Workspace getWorkspaceByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Workspaces WHERE name = ?",
                workspaceRowMapper,
                name);
    }

    public List<Workspace> getAllWorkspaces() {
        return jdbcTemplate.query(
                "SELECT * FROM Workspaces",
                workspaceRowMapper);
    }

    public int updateWorkspace(int id, String name, String description, WorkspaceTypeEnum type) {
        return jdbcTemplate.update(
                "UPDATE Workspaces SET name = ?, description = ?, type = ? WHERE id = ?",
                name, description, type.name(), id);
    }

    public int deleteWorkspace(int id) {
        return jdbcTemplate.update(
                "DELETE FROM Workspaces WHERE id = ?",
                id);
    }
}