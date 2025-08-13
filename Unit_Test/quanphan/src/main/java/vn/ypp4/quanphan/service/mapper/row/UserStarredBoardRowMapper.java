package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.UserFavoritedBoard;

@Component
public class UserStarredBoardRowMapper extends BaseRowMapper<UserFavoritedBoard> {

    @Override
    protected UserFavoritedBoard mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new UserFavoritedBoard(
                rs.getInt("UserId"),
                rs.getInt("BoardId"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getBoolean("StarredBoardsStatus"));
    }
}
