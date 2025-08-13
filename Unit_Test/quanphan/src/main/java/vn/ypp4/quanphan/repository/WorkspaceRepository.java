package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.service.mapper.row.WorkspaceRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkspaceRepository {
    private final JdbcTemplate jdbcTemplate;
    private final WorkspaceRowMapper workspaceRowMapper;

    public List<Workspace> findAccessibleWorkspacesByUserId(int userId){
        String sql = "SELECT w.Id, w.WorkspaceName, w.WorkspaceDescription, w.TypeId, " +
                    "w.CreatedAt, w.CreatedBy, w.UpdatedAt, w.UpdatedBy, w.LogoUrl " +
                    "FROM Workspace w " +
                    "JOIN Members m ON m.WorkspaceId = w.Id " +
                    "WHERE m.UserId = ?";
        return jdbcTemplate.query(sql, workspaceRowMapper, userId);
    }
}
