package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Attachment;

import vn.ypp4.quanphan.service.mapper.AttachmentRowMapper;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final AttachmentRowMapper attachmentRowMapper;

    public Attachment createAttachment(int cardId, int attachmentTypeId, String attachmentPath,
            String attachmentName, LocalDateTime createdAt) {
        if (attachmentPath == null || attachmentPath.isBlank()) {
            throw new IllegalArgumentException("Attachment path cannot be null or empty");
        }
        if (attachmentName == null || attachmentName.isBlank()) {
            throw new IllegalArgumentException("Attachment name cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        jdbcTemplate.update(
                "INSERT INTO Attachment (CardId, AttachmentTypeId, AttachmentPath, AttachmentName, CreatedAt) " +
                        "VALUES (?, ?, ?, ?, ?)",
                cardId, attachmentTypeId, attachmentPath, attachmentName, createdAt);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Attachment WHERE Id = LAST_INSERT_ID()",
                attachmentRowMapper);
    }

    public Attachment getAttachmentById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Attachment WHERE Id = ?",
                attachmentRowMapper,
                id);
    }

    public List<Attachment> getAttachmentsByCardId(int cardId) {
        return jdbcTemplate.query(
                "SELECT * FROM Attachment WHERE CardId = ? ORDER BY CreatedAt DESC",
                attachmentRowMapper,
                cardId);
    }

    public List<Attachment> getAttachmentsByType(int attachmentTypeId) {
        return jdbcTemplate.query(
                "SELECT * FROM Attachment WHERE AttachmentTypeId = ? ORDER BY CreatedAt DESC",
                attachmentRowMapper,
                attachmentTypeId);
    }

    public int updateAttachment(int id, String attachmentPath, String attachmentName) {
        if (attachmentPath == null || attachmentPath.isBlank()) {
            throw new IllegalArgumentException("Attachment path cannot be null or empty");
        }
        if (attachmentName == null || attachmentName.isBlank()) {
            throw new IllegalArgumentException("Attachment name cannot be null or empty");
        }

        return jdbcTemplate.update(
                "UPDATE Attachment SET AttachmentPath = ?, AttachmentName = ? WHERE Id = ?",
                attachmentPath, attachmentName, id);
    }

    public int deleteAttachment(int id) {
        return jdbcTemplate.update(
                "DELETE FROM Attachment WHERE Id = ?",
                id);
    }
}
