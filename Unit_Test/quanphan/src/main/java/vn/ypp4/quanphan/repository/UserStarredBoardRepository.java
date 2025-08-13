package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.domain.UserFavoritedBoard;
import vn.ypp4.quanphan.service.mapper.row.BoardRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserStarredBoardRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BoardRowMapper boardRowMapper;
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
        return jdbcTemplate.query(sql,boardRowMapper,userId);
    }

    void createUser(UserFavoritedBoard starred){
        String sql="INSERT INTO UserStarredBoard (UserId, BoardId) VALUES (?, ?)";
        jdbcTemplate.update(sql, starred.getUserId(), starred.getBoardId());
    }
}
