package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.PowerUpCategory;
@Component
public class PowerUpCategoryRowMapper extends BaseRowMapper<PowerUpCategory> {

    @Override
    protected PowerUpCategory mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new PowerUpCategory(
                rs.getInt("Id"),
                rs.getString("CategoryValue"),
                rs.getString("DisplayValue"));
    }
}
