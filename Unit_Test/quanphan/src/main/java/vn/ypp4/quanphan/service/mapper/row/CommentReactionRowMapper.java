package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.CommentReaction;

public class CommentReactionRowMapper extends BaseRowMapper<CommentReaction> {

    @Override
    protected CommentReaction mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new CommentReaction(
                rs.getInt("CommentId"),
                rs.getInt("ReactionId"),
                rs.getInt("CreatedBy"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null);
    }
}
