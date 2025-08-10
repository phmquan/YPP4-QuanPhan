package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Notification;

public class NotificationRowMapper extends BaseRowMapper<Notification> {

    @Override
    protected Notification mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Notification(
                rs.getInt("Id"),
                rs.getInt("ActivityId"),
                rs.getBoolean("IsRead"));
    }
}
