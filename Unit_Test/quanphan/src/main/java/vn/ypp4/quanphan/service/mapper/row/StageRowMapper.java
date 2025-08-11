package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Stage;

public class StageRowMapper extends BaseRowMapper<Stage> {

    @Override
    protected Stage mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Stage(
                rs.getInt("Id"),
                rs.getString("Title"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getInt("BoardId"),
                rs.getString("StageStatus"),
                rs.getInt("ColorId"),
                rs.getInt("Position"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"));
    }
}
