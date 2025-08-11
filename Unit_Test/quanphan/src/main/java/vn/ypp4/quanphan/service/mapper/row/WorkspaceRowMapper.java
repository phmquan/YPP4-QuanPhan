package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Workspace;

public class WorkspaceRowMapper extends BaseRowMapper<Workspace> {

    @Override
    protected Workspace mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Workspace(
                rs.getInt("Id"),
                rs.getString("WorkspaceName"),
                rs.getString("WorkspaceDescription"),
                rs.getInt("TypeId"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"),
                rs.getString("LogoUrl"));
    }

}
