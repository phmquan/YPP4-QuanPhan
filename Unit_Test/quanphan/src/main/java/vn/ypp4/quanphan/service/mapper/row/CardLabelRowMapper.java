package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.CardLabel;
@Component
public class CardLabelRowMapper extends BaseRowMapper<CardLabel> {

    @Override
    protected CardLabel mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CardLabel(
                rs.getInt("CardId"),
                rs.getInt("LabelId"));
    }
}
