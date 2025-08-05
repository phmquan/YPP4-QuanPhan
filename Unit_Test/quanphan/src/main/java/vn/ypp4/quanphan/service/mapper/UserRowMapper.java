package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.User;

public class UserRowMapper extends BaseRowMapper<User> {

    @Override
    protected User mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("Id"),
                rs.getString("Username"),
                rs.getString("Bio"),
                rs.getString("Email"),
                rs.getTimestamp("LastActive").toInstant(),
                rs.getTimestamp("CreatedAt").toInstant(),
                rs.getString("PictureUrl"));
    }

}
