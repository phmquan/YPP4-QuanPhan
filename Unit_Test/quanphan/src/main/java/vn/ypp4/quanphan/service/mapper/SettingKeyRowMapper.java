package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.SettingKey;

public class SettingKeyRowMapper extends BaseRowMapper<SettingKey> {

    @Override
    protected SettingKey mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new SettingKey(
                rs.getInt("Id"),
                rs.getString("KeyName"),
                rs.getString("SettingKeyDescription"),
                rs.getInt("OwnerTypeId"),
                rs.getInt("DefaultValue"),
                rs.getBoolean("IsBoolean"));
    }
}
