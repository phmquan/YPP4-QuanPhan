package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.CustomFieldValue;
@Component
public class CustomFieldValueRowMapper extends BaseRowMapper<CustomFieldValue> {

    @Override
    protected CustomFieldValue mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CustomFieldValue(
                rs.getInt("Id"),
                rs.getInt("CustomFieldId"),
                rs.getInt("CardId"),
                rs.getString("Value"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"));
    }
}
