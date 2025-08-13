package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Template;

@Component
public class TemplateRowMapper extends BaseRowMapper<Template> {

    @Override
    protected Template mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Template(
                rs.getInt("Id"),
                rs.getString("Title"),
                rs.getString("TemplateDescription"),
                rs.getInt("CategoryId"),
                rs.getInt("Viewed"),
                rs.getInt("Copied"),
                rs.getInt("CreatedBy"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"),
                rs.getInt("BoardId"),
                rs.getString("BackgroundUrl"));
    }
}
