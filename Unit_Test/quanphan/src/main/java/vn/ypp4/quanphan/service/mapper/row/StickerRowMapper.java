package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Sticker;

public class StickerRowMapper extends BaseRowMapper<Sticker> {

    @Override
    protected Sticker mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Sticker(
                rs.getInt("Id"),
                rs.getInt("CategoryId"),
                rs.getString("StickerName"),
                rs.getString("StickerUrl"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"));
    }
}
