package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.Collections;
@Component
public class CollectionsRowMapper extends BaseRowMapper<Collections> {

    @Override
    protected Collections mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Collections(
                rs.getInt("Id"),
                rs.getString("CollectionName"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"),
                rs.getInt("WorkspaceId"));
    }
}
