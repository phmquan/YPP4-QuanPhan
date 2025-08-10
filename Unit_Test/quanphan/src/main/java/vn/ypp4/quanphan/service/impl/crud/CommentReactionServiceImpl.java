package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.CommentReaction;
import vn.ypp4.quanphan.service.mapper.CommentReactionRowMapper;

@Service
@RequiredArgsConstructor
public class CommentReactionServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CommentReactionRowMapper commentReactionRowMapper;
    @Transactional
    public CommentReaction addReactionToComment(int commentId, int reactionId, int createdBy, LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        
        // Check if user already reacted with this reaction
        if (hasUserReactedToComment(commentId, createdBy)) {
            // Update existing reaction
            jdbcTemplate.update(
                "UPDATE CommentReaction SET ReactionId = ?, CreatedAt = ? " +
                "WHERE CommentId = ? AND CreatedBy = ?",
                reactionId, createdAt, commentId, createdBy);
        } else {
            // Add new reaction
            jdbcTemplate.update(
                "INSERT INTO CommentReaction (CommentId, ReactionId, CreatedBy, CreatedAt) " +
                "VALUES (?, ?, ?, ?)",
                commentId, reactionId, createdBy, createdAt);
        }
        
        return jdbcTemplate.queryForObject(
            "SELECT * FROM CommentReaction WHERE CommentId = ? AND CreatedBy = ?",
            commentReactionRowMapper,
            commentId, createdBy);
    }
    @Transactional
    public void removeReactionFromComment(int commentId, int reactionId, int userId) {
        jdbcTemplate.update(
            "DELETE FROM CommentReaction WHERE CommentId = ? AND ReactionId = ? AND CreatedBy = ?",
            commentId, reactionId, userId);
    }
    public List<CommentReaction> getReactionsForComment(int commentId) {
        return jdbcTemplate.query(
            "SELECT * FROM CommentReaction WHERE CommentId = ? ORDER BY CreatedAt",
            commentReactionRowMapper,
            commentId);
    }
    public List<CommentReaction> getReactionsByUser(int userId) {
        return jdbcTemplate.query(
            "SELECT * FROM CommentReaction WHERE CreatedBy = ? ORDER BY CreatedAt DESC",
            commentReactionRowMapper,
            userId);
    }
    public boolean hasUserReactedToComment(int commentId, int userId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM CommentReaction WHERE CommentId = ? AND CreatedBy = ?",
            Integer.class,
            commentId, userId);
        return count != null && count > 0;
    }
    public int getReactionCountForComment(int commentId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM CommentReaction WHERE CommentId = ?",
            Integer.class,
            commentId);
        return count != null ? count : 0;
    }
}
