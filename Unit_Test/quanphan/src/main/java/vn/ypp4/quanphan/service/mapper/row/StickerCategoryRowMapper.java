package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.StickerCategory;

@Component
public class StickerCategoryRowMapper extends BaseRowMapper<StickerCategory> {

    @Override
    protected StickerCategory mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new StickerCategory(
                rs.getInt("Id"),
                rs.getString("CategoryValue"),
                rs.getString("DisplayValue"));
    }
}
