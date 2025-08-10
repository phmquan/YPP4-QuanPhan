package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.FieldValue;

public class FieldValueRowMapper extends BaseRowMapper<FieldValue> {

    @Override
    protected FieldValue mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new FieldValue(
                rs.getInt("Id"),
                rs.getInt("CardId"),
                rs.getString("FieldValue"),
                rs.getInt("CustomFieldId"));
    }
}
