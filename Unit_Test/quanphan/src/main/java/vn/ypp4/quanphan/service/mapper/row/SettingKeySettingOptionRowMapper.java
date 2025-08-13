package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.SettingKeySettingOption;

@Component
public class SettingKeySettingOptionRowMapper extends BaseRowMapper<SettingKeySettingOption> {

    @Override
    protected SettingKeySettingOption mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new SettingKeySettingOption(
                rs.getInt("SettingKeyId"),
                rs.getInt("SettingOptionId"));
    }
}
