package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.entity.Board;
import vn.ypp4.quanphan.domain.entity.UserStarredBoard;


import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserStarredBoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Board> getStarredBoardsByUserId(int userId){
        String sql="select \n" +
                "  b.Id, \n" +
                "  b.BoardName, \n" +
                "  b.BoardDescription, \n" +
                "  b.CreatedAt, \n" +
                "  b.CreatedBy, \n" +
                "  b.BackgroundUrl, \n" +
                "  b.WorkspaceId, \n" +
                "  b.BoardStatus, \n" +
                "  b.UpdatedAt, \n" +
                "  b.UpdatedBy \n" +
                "from \n" +
                "  UserStarredBoard usb \n" +
                "  join Board b on b.Id = usb.BoardId \n" +
                "where \n" +
                "  usb.UserId = ? \n" +
                "order by \n" +
                "  usb.CreatedAt desc\n";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Board.class)
                ,userId);
    }

    void createUser(UserStarredBoard starred){
        String sql="INSERT INTO UserStarredBoard (UserId, BoardId) VALUES (?, ?)";
        jdbcTemplate.update(sql, starred.getUserId(), starred.getBoardId());
    }

    public List<Board> getStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        String sql = "SELECT b.Id, b.BoardName, b.BoardDescription, b.CreatedAt, b.CreatedBy, " +
                "b.BackgroundUrl, b.WorkspaceId, b.BoardStatus, b.UpdatedAt, b.UpdatedBy " +
                "FROM UserStarredBoard usb " +
                "JOIN Board b ON b.Id = usb.BoardId " +
                "WHERE usb.UserId = ? AND b.WorkspaceId = ? " +
                "ORDER BY usb.CreatedAt DESC";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Board.class),
                userId, workspaceId);
    }
}
