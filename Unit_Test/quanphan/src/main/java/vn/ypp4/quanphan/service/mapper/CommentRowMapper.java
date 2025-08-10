package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Comment;

public class CommentRowMapper extends BaseRowMapper<Comment> {

    @Override
    protected Comment mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
                rs.getInt("Id"),
                rs.getString("Content"),
                rs.getInt("CardId"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"));
    }
}
