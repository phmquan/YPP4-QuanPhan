package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Color;

public class ColorRowMapper extends BaseRowMapper<Color> {

    @Override
    protected Color mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Color(
                rs.getInt("Id"),
                rs.getString("ColorName"),
                rs.getString("ColorHex"),
                rs.getString("Icon"));
    }
}
