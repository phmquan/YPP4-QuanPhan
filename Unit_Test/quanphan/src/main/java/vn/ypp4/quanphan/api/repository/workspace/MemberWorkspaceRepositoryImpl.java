package vn.ypp4.quanphan.api.repository.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.api.dto.member.MemberWorkspaceResponseDTO;

@Repository
@RequiredArgsConstructor
public class MemberWorkspaceRepositoryImpl implements MemberWorkspaceRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public MemberWorkspaceResponseDTO getWorkspaceMembersByUserId(int workspaceId) {
        String sql =
                """
                    WITH WorkspaceMembers AS (
                        SELECT 
                            m.UserId, 
                            m.RolePermissionId
                        FROM Members m
                            JOIN OwnerType owt ON owt.Id = m.OwnerTypeId
                        WHERE owt.OwnerTypeValue = 'workspace' 
                            AND m.OwnerId = ?
                    ),
                    BoardInWorkspace AS (
                        SELECT 
                            b.Id AS BoardId,
                            b.BoardName,
                            b.BackgroundUrl
                        FROM Board b
                        WHERE b.WorkspaceId = ?
                    ),
                    BoardMembers AS (
                        SELECT 
                            m.UserId,
                            m.OwnerId AS BoardId
                        FROM Members m
                            JOIN OwnerType owt ON owt.Id = m.OwnerTypeId
                        WHERE owt.OwnerTypeValue = 'board'
                    )
                    SELECT
                        u.Id AS UserId,
                        u.Username, 
                        u.Email AS UserEmail,
                        u.LastActive,
                        p.PermissionName AS Permission,
                        COUNT(bm.BoardId) AS NumBoardsJoined,
                        STRING_AGG(biw.BoardName, ', ') AS JoinedBoardNames,
                        STRING_AGG(biw.BackgroundUrl, ', ') AS JoinedBoardBackground
                    FROM WorkspaceMembers wm
                        LEFT JOIN BoardMembers bm ON bm.UserId = wm.UserId
                        JOIN BoardInWorkspace biw ON bm.BoardId = biw.BoardId
                        JOIN Users u ON wm.UserId = u.Id
                        JOIN RolePermission p ON wm.RolePermissionId = p.Id
                    GROUP BY 
                        u.Id, 
                        u.Username,
                        u.Email,
                        u.LastActive, 
                        p.PermissionName;
                """;
        return jdbcTemplate.queryForObject(
            sql,
            new Object[]{workspaceId, workspaceId},
            new BeanPropertyRowMapper<>(MemberWorkspaceResponseDTO.class)
        );
    }
}
