package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.CheckList;
@Component
public class CheckListRowMapper extends BaseRowMapper<CheckList> {

    @Override
    protected CheckList mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CheckList(
                rs.getInt("Id"),
                rs.getString("CheckListName"),
                rs.getInt("CardId"),
                rs.getInt("Position"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"));
    }
}
