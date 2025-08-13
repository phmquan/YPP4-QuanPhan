package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.WorkspaceMembershipDomain;

@Component
public class WorkspaceMembershipDomainRowMapper extends BaseRowMapper<WorkspaceMembershipDomain> {

    @Override
    protected WorkspaceMembershipDomain mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new WorkspaceMembershipDomain(
                rs.getInt("Id"),
                rs.getInt("WorkspaceId"),
                rs.getString("Domain"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null);
    }
}
