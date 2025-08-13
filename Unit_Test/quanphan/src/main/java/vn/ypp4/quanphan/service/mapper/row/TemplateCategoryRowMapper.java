package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.TemplateCategory;

@Component
public class TemplateCategoryRowMapper extends BaseRowMapper<TemplateCategory> {

    @Override
    protected TemplateCategory mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new TemplateCategory(
                rs.getInt("Id"),
                rs.getString("CategoryValue"),
                rs.getString("DisplayValue"),
                rs.getString("IconUrl"));
    }
}
