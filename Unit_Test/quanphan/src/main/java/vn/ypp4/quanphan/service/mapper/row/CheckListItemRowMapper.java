package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.CheckListItem;

public class CheckListItemRowMapper extends BaseRowMapper<CheckListItem> {

    @Override
    protected CheckListItem mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CheckListItem(
                rs.getInt("Id"),
                rs.getString("CheckListItemName"),
                rs.getInt("MemberId"),
                rs.getInt("CheckListId"),
                rs.getDate("DueDate") != null ? rs.getDate("DueDate").toLocalDate() : null,
                rs.getBoolean("CheckListItemStatus"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"),
                rs.getInt("Position"));
    }
}
