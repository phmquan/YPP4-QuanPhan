package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.WorkspaceType;

public class WorkspaceTypeRowMapper extends BaseRowMapper<WorkspaceType> {

    @Override
    protected WorkspaceType mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new WorkspaceType(
                rs.getInt("Id"),
                rs.getString("TypeValue"),
                rs.getString("DisplayValue"));
    }
}
