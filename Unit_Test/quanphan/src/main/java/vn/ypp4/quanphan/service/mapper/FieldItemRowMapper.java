package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.FieldItem;

public class FieldItemRowMapper extends BaseRowMapper<FieldItem> {

    @Override
    protected FieldItem mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new FieldItem(
                rs.getInt("Id"),
                rs.getInt("ColorId"),
                rs.getString("FieldItemValue"),
                rs.getInt("Position"),
                rs.getInt("CustomFieldId"));
    }
}
