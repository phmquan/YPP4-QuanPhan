package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.CustomField;

public class CustomFieldRowMapper extends BaseRowMapper<CustomField> {

    @Override
    protected CustomField mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CustomField(
                rs.getInt("Id"),
                rs.getString("Title"),
                rs.getInt("DataTypeId"),
                rs.getInt("BoardId"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"),
                rs.getInt("Position"),
                rs.getBoolean("IsFrontCardShowed"));
    }
}
