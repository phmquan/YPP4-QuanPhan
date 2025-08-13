package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.Members;
@Component
public class MembersRowMapper extends BaseRowMapper<Members> {

    @Override
    protected Members mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Members(
                rs.getInt("Id"),
                rs.getInt("UserId"),
                rs.getInt("RolePermissonId"),
                rs.getInt("OwnerTypeId"),
                rs.getInt("OwnerId"),
                rs.getInt("InvitedBy"),
                rs.getTimestamp("JoinedAt") != null ? rs.getTimestamp("JoinedAt").toLocalDateTime() : null,
                rs.getString("MemberStatus"));
    }
}
