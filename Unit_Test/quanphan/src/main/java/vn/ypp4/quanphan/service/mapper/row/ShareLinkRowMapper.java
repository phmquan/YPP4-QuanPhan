package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.ShareLink;

@Component
public class ShareLinkRowMapper extends BaseRowMapper<ShareLink> {

    @Override
    protected ShareLink mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new ShareLink(
                rs.getInt("Id"),
                rs.getInt("OwnerTypeId"),
                rs.getInt("RolePermissionId"),
                rs.getInt("OwnerId"),
                rs.getString("ShareLinkToken"),
                rs.getBoolean("ShareLinkStatus"));
    }
}
