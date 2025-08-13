package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.User;

@Component
public class UserRowMapper extends BaseRowMapper<User> {

    @Override
    protected User mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("Id"),
                rs.getString("Username"),
                rs.getString("FullName"),
                rs.getString("Bio"),
                rs.getString("Email"),
                rs.getTimestamp("LastActive") != null ? rs.getTimestamp("LastActive").toLocalDateTime() : null,
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getString("PictureUrl"));
    }

}
