package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.SettingValue;

public class SettingValueRowMapper extends BaseRowMapper<SettingValue> {

    @Override
    protected SettingValue mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new SettingValue(
                rs.getInt("Id"),
                rs.getInt("SettingKeyId"),
                rs.getInt("SettingContent"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"),
                rs.getInt("OwnerId"));
    }
}
