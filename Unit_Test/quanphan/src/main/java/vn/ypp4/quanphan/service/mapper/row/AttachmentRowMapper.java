package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Attachment;

public class AttachmentRowMapper extends BaseRowMapper<Attachment> {

    @Override
    protected Attachment mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Attachment(
                rs.getInt("Id"),
                rs.getInt("CardId"),
                rs.getInt("AttachmentTypeId"),
                rs.getString("AttachmentPath"),
                rs.getString("AttachmentName"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getString("Size"),
                rs.getBoolean("IsCover"));
    }
}
