package vn.ypp4.quanphan.repository.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.dto.member.MemberWorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceUpdateDTO;

import java.util.List;

@Repository

public class WorkspaceRepositoryImpl implements WorkspaceRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String WORKSPACE_PROJECTION=
            "SELECT \n" +
            "  w.Id, \n" +
            "  w.WorkspaceName, \n" +
            "  w.WorkspaceDescription \n" +
            "FROM \n" +
            "  Workspace w \n";
    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM Workspace WHERE Id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public int update(WorkspaceUpdateDTO updateWorkspace) {
        String sql =
                """
                    UPDATE\s
                      Workspace\s
                    SET\s
                      WorkspaceName = ?,\s
                      WorkspaceDescription = ?\s
                    WHERE\s
                      Id = ?
                """;
        return jdbcTemplate.update(sql,updateWorkspace.getWorkspaceName(),
                updateWorkspace.getWorkspaceDescription(),updateWorkspace.getId());

    }

    @Override
    public WorkspaceResponseDTO findById(int workspaceId) {
        String sql= WORKSPACE_PROJECTION+
                "WHERE \n" +
                " Id = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(WorkspaceResponseDTO.class),workspaceId);
    }

    @Override
    public List<WorkspaceResponseDTO> findMemberWorkspacesByUserId(int userId) {
        String sql= WORKSPACE_PROJECTION+
                "JOIN Members m ON m.OwnerId = w.Id \n" +
                "JOIN OwnerType owt ON owt.Id = m.OwnerTypeId \n" +
                "WHERE \n" +
                "  m.UserId = ? \n" +
                "  AND owt.OwnerTypeValue = 'workspace'";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(WorkspaceResponseDTO.class),userId);
    }


}
