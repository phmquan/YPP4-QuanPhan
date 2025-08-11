package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.CategoryType;

public class CategoryTypeRowMapper extends BaseRowMapper<CategoryType> {

    @Override
    protected CategoryType mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CategoryType(
                rs.getInt("Id"),
                rs.getString("CategoryTypeValue"));
    }
}
