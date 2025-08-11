package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Comment;
import vn.ypp4.quanphan.service.mapper.row.CommentRowMapper;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CommentRowMapper commentRowMapper;

    @Transactional
    public Comment createComment(String content, int cardId, LocalDateTime createdAt, int createdBy) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment content cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        // Set updatedAt to createdAt if not provided
        LocalDateTime updatedAt = createdAt;
        int updatedBy = createdBy;

        jdbcTemplate.update(
                "INSERT INTO Comment (Content, CardId, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                content, cardId, createdAt, createdBy, updatedAt, updatedBy);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Comment WHERE Id = LAST_INSERT_ID()",
                commentRowMapper);
    }

    public Comment getCommentById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Comment WHERE Id = ?",
                commentRowMapper,
                id);
    }

    public List<Comment> getCommentsByCard(int cardId) {
        return jdbcTemplate.query(
                "SELECT * FROM Comment WHERE CardId = ? ORDER BY CreatedAt DESC",
                commentRowMapper,
                cardId);
    }

    public List<Comment> getCommentsByUser(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM Comment WHERE CreatedBy = ? ORDER BY CreatedAt DESC",
                commentRowMapper,
                userId);
    }

    @Transactional
    public int updateComment(int id, String content, LocalDateTime updatedAt, int updatedBy) {
        if (content != null && content.isBlank()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }

        // Get existing comment to merge with updates
        Comment existingComment = getCommentById(id);

        String finalContent = content != null ? content : existingComment.getContent();

        return jdbcTemplate.update(
                "UPDATE Comment SET Content = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
                finalContent, updatedAt, updatedBy, id);
    }

    public int deleteComment(int id) {
        return jdbcTemplate.update(
                "DELETE FROM Comment WHERE Id = ?",
                id);
    }
}
