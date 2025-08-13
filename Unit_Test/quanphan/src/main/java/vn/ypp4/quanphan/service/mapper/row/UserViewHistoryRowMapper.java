package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.UserViewHistory;

@Component
public class UserViewHistoryRowMapper extends BaseRowMapper<UserViewHistory> {

    @Override
    protected UserViewHistory mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new UserViewHistory(
                rs.getInt("UserId"),
                rs.getInt("OwnerTypeId"),
                rs.getInt("OwnerId"),
                rs.getTimestamp("AccessedAt") != null ? rs.getTimestamp("AccessedAt").toLocalDateTime() : null);
    }
}
