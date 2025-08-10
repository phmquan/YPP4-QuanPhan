package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.SettingOption;

public class SettingOptionRowMapper extends BaseRowMapper<SettingOption> {

    @Override
    protected SettingOption mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new SettingOption(
                rs.getInt("Id"),
                rs.getString("DisplayValue"),
                rs.getString("SettingOptionValue"));
    }
}
