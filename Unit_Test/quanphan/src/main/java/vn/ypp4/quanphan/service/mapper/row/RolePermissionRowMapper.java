package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.RolePermission;

@Component
public class RolePermissionRowMapper extends BaseRowMapper<RolePermission> {

    @Override
    protected RolePermission mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new RolePermission(
                rs.getInt("Id"),
                rs.getString("PermissionName"),
                rs.getString("PermissionCode"));
    }
}
