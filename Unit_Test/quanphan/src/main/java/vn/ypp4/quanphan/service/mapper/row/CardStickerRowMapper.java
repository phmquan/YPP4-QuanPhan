package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.CardSticker;

public class CardStickerRowMapper extends BaseRowMapper<CardSticker> {

    @Override
    protected CardSticker mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CardSticker(
                rs.getInt("CardId"),
                rs.getInt("StickerId"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getFloat("PositionX"),
                rs.getFloat("PositionY"),
                rs.getInt("IndexZ"));
    }
}
