package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Activity;

public class ActivityRowMapper extends BaseRowMapper<Activity> {

    @Override
    protected Activity mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Activity(
                rs.getInt("Id"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getString("ActivityDescription"),
                rs.getInt("UserId"),
                rs.getInt("CategoryId"),
                rs.getInt("OwnerId"));
    }
}
