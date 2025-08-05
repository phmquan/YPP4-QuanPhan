package vn.ypp4.quanphan.service.mapper;

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
                rs.getInt("CategoryId"),
                rs.getTimestamp("CreatedAt").toInstant(),
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toInstant() : null,
                rs.getInt("UpdatedBy"),
                rs.getString("LogoUrl"));
    }

}
