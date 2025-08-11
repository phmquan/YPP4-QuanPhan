package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.OwnerType;

public class OwnerTypeRowMapper extends BaseRowMapper<OwnerType> {

    @Override
    protected OwnerType mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new OwnerType(
                rs.getInt("Id"),
                rs.getString("OwnerTypeValue"));
    }
}
