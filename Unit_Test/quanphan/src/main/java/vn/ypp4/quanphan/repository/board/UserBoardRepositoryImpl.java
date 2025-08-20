package vn.ypp4.quanphan.repository.board;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import vn.ypp4.quanphan.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customDI.annotation.MyRepository;
import vn.ypp4.quanphan.customDI.annotation.MyService;
import vn.ypp4.quanphan.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.dto.board.BoardResponseDTO;

import java.util.List;

@Repository
@NoArgsConstructor
public class UserBoardRepositoryImpl implements UserBoardRepository {
    @Autowired
    @MyAutowired
    private  JdbcTemplate jdbcTemplate;
    private static final String BOARD_PROJECTION =
                    """
                        SELECT
                         b.Id as BoardId,\s
                         b.BoardName,\s
                         b.BackgroundUrl\s
                        FROM
                          Board b
                    """;
    @Override
    @Cacheable(cacheNames = "starredBoards",key="'userId:' + #userId")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<BoardResponseDTO> findStarredBoardsByUserId(int userId) {
        String sql=
                BOARD_PROJECTION+
                "  JOIN UserStarredBoard usb ON b.Id = usb.BoardId \n" +
                "WHERE \n" +
                "  usb.UserId = ? \n" +
                "ORDER BY \n" +
                "  usb.CreatedAt desc";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(BoardResponseDTO.class)
                ,userId);
    }

    @Override


    @Cacheable(cacheNames = "viewedBoards",key="'userId:'+ #userId")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<BoardResponseDTO> findViewedBoardsByUserId(int userId) {
        String sql=
                BOARD_PROJECTION+
                "  JOIN UserViewHistory uvh ON uvh.OwnerId = b.Id " +
                "  JOIN OwnerType owt ON owt.Id = uvh.OwnerTypeId " +
                "  AND owt.OwnerTypeValue = 'board' " +
                "  JOIN Users u ON u.Id = uvh.UserId " +
                "WHERE " +
                "  u.Id = ? -- userId" +
                "ORDER BY " +
                "  uvh.AccessedAt DESC;";

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(BoardResponseDTO.class),
                userId);
    }


    @Override
    public BoardResponseDTO findById(int boardId) {
        String sql= BOARD_PROJECTION+
                "WHERE \n" +
                "   b.Id = ?";
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(BoardResponseDTO.class),
                boardId
                );
    }

    @Override
    @Cacheable(cacheNames = "starredBoardsWorkspace",key = "{#userId, #workspaceId}")
    public List<BoardResponseDTO> findStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        String sql=BOARD_PROJECTION+
                "JOIN UserStarredBoard usb ON b.Id = usb.BoardId \n" +
                "WHERE \n" +
                "  usb.UserId = ? \n" +
                "AND b.WorkspaceId = ? \n" +
                "ORDER BY \n" +
                "  usb.CreatedAt desc";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(BoardResponseDTO.class),
                userId,
                workspaceId
                );
    }

    @Override
    @Cacheable(cacheNames = "memberBoards",key = "'userId:'+#userId")
    public List<BoardResponseDTO> findMemberBoardsByUserId(int userId) {
        String sql=BOARD_PROJECTION+
                "JOIN Members m on m.OwnerId=b.Id\n"+
                "JOIN OwnerType owt ON owt.Id = m.OwnerTypeId \n"+
                "WHERE m.UserId = ?\n"+
                "AND owt.OwnerTypeValue = 'board'";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(BoardResponseDTO.class),
                userId);
    }

    @Override
    @Cacheable(cacheNames = "memberBoardsWorkspace",key = "{#userId, #workspaceId}")
    public List<BoardResponseDTO> findMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        String sql= BOARD_PROJECTION+
                "JOIN Members m on m.OwnerId=b.Id\n"+
                "JOIN OwnerType owt ON owt.Id = m.OwnerTypeId \n"+
                "WHERE m.UserId = ?\n"+
                "AND owt.OwnerTypeValue = 'board'\n"+
                "AND b.WorkspaceId=?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(BoardResponseDTO.class),
                userId,
                workspaceId
                );
    }
}
