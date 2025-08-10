package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.ReactionCategory;

public class ReactionCategoryRowMapper extends BaseRowMapper<ReactionCategory> {

    @Override
    protected ReactionCategory mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new ReactionCategory(
                rs.getInt("Id"),
                rs.getString("CategoryValue"),
                rs.getString("DisplayValue"));
    }
}
