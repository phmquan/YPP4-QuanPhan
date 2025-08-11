package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.CardCoverType;

public class CardCoverTypeRowMapper extends BaseRowMapper<CardCoverType> {

    @Override
    protected CardCoverType mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CardCoverType(
                rs.getInt("Id"),
                rs.getString("TypeValue"),
                rs.getString("DisplayValue"));
    }
}
