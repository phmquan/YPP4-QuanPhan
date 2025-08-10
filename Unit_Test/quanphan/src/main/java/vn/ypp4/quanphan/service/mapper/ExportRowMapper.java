package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Export;

public class ExportRowMapper extends BaseRowMapper<Export> {

    @Override
    protected Export mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Export(
                rs.getInt("Id"),
                rs.getInt("WorkspaceId"),
                rs.getInt("CreatedBy"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("Size"));
    }
}
