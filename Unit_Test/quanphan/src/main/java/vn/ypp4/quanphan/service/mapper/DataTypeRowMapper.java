package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.DataType;

public class DataTypeRowMapper extends BaseRowMapper<DataType> {

    @Override
    protected DataType mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new DataType(
                rs.getInt("Id"),
                rs.getString("DataTypeValue"));
    }
}
