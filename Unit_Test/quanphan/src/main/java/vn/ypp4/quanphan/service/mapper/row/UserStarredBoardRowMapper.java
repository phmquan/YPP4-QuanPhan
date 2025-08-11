package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.UserStarredBoard;

public class UserStarredBoardRowMapper extends BaseRowMapper<UserStarredBoard> {

    @Override
    protected UserStarredBoard mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new UserStarredBoard(
                rs.getInt("UserId"),
                rs.getInt("BoardId"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getBoolean("StarredBoardsStatus"));
    }
}
