package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.PowerUpCategory;

public class PowerUpCategoryRowMapper extends BaseRowMapper<PowerUpCategory> {

    @Override
    protected PowerUpCategory mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new PowerUpCategory(
                rs.getInt("Id"),
                rs.getString("CategoryValue"),
                rs.getString("DisplayValue"));
    }
}
