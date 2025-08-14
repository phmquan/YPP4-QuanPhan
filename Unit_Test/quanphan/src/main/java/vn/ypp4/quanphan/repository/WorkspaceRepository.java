package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.dto.WorkspaceResponseDTO;
import vn.ypp4.quanphan.domain.entity.Workspace;


import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkspaceRepository {
    private final JdbcTemplate jdbcTemplate;


    public List<Workspace> findAccessibleWorkspacesByUserId(int userId){
        String sql = "SELECT w.Id, w.WorkspaceName, w.WorkspaceDescription, w.TypeId, " +
                    "w.CreatedAt, w.CreatedBy, w.UpdatedAt, w.UpdatedBy, w.LogoUrl " +
                    "FROM Workspace w " +
                    "JOIN Members m ON m.WorkspaceId = w.Id " +
                    "WHERE m.UserId = ?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Workspace.class)
                , userId);
    }

    public boolean existById(int workspaceId) {
        String sql = "SELECT COUNT(*) > 0 FROM Workspace WHERE Id = ?";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, workspaceId);
        return exists != null && exists;
    }

    public List<Workspace> findMemberWorkspacesByUserId(int userId) {
        String sql="\n" +
                "SELECT \n" +
                "    w.Id AS WorkspaceId,\n" +
                "    w.WorkspaceName,\n" +
                "    w.LogoUrl AS WorkspaceIcon,\n" +
                "    w.WorkspaceDescription "+
                "FROM Workspace w\n" +
                "    -- Find Workspace where User is a Member\n" +
                "    JOIN Members mw ON mw.OwnerId = w.Id\n" +
                "    JOIN OwnerType otw ON otw.Id = mw.OwnerTypeId \n" +
                "        AND otw.OwnerTypeValue = 'workspace'\n"+
                "WHERE mw.UserId = ?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Workspace.class)
                ,userId);
    }

    public Workspace findById(int workspaceId) {
        String sql="SELECT \n" +
                "    w.Id AS WorkspaceId,\n" +
                "    w.WorkspaceName,\n" +
                "    w.LogoUrl,\n" +
                "    w.WorkspaceDescription,\n" +
                "    w.TypeId,\n" +
                "    w.CreatedAt,\n" +
                "    w.CreatedBy,\n" +
                "    w.UpdatedAt,\n" +
                "    w.UpdatedBy\n" +
                "FROM Workspace w\n" +
                "WHERE w.Id = ?";
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(Workspace.class),
                workspaceId);
    }
}
