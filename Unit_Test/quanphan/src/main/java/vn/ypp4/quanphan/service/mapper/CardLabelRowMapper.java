package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.CardLabel;

public class CardLabelRowMapper extends BaseRowMapper<CardLabel> {

    @Override
    protected CardLabel mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CardLabel(
                rs.getInt("CardId"),
                rs.getInt("LabelId"));
    }
}
