package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.Category;
@Component
public class CategoryRowMapper extends BaseRowMapper<Category> {

    @Override
    protected Category mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Category(
                rs.getInt("Id"),
                rs.getString("CategoryName"),
                rs.getString("CategoryDescription"),
                rs.getInt("CategoryTypeId"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getString("Icon"),
                rs.getInt("Position"),
                rs.getBoolean("IsActive"));
    }
}
