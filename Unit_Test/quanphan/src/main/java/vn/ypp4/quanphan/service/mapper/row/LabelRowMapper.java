package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Label;

public class LabelRowMapper extends BaseRowMapper<Label> {

    @Override
    protected Label mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Label(
                rs.getInt("Id"),
                rs.getString("Title"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"),
                rs.getInt("ColorId"),
                rs.getBoolean("IsDefault"),
                rs.getInt("BoardId"));
    }
}
