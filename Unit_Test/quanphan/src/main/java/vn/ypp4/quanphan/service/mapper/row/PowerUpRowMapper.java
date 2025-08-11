package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.PowerUp;

public class PowerUpRowMapper extends BaseRowMapper<PowerUp> {

    @Override
    protected PowerUp mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new PowerUp(
                rs.getInt("Id"),
                rs.getString("PowerUpName"),
                rs.getString("IconUrl"),
                rs.getString("BackgroundUrl"),
                rs.getString("AuthorName"),
                rs.getString("PowerUpDescription"),
                rs.getString("EmailContact"),
                rs.getString("PolicyUrl"),
                rs.getObject("IsStaffPick") != null ? rs.getBoolean("IsStaffPick") : null,
                rs.getObject("IsIntegration") != null ? rs.getBoolean("IsIntegration") : null,
                rs.getInt("CategoryId"));
    }
}
