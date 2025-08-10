package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Cards;
import vn.ypp4.quanphan.service.mapper.CardsRowMapper;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CardsRowMapper cardsRowMapper;
    @Transactional
    public Cards createCard(int stageId, String title, String cardDescription, LocalDateTime createdAt,
            int createdBy, String cardStatus, String cardLocation, LocalDate startDate,
            LocalDate dueDate, int cardCoverTypeId, String coverValue, int position,
            LocalDateTime updatedAt, int updatedBy) {
        
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Card title cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        
        // Set updatedAt to createdAt if not provided
        LocalDateTime finalUpdatedAt = updatedAt != null ? updatedAt : createdAt;
        int finalUpdatedBy = updatedBy > 0 ? updatedBy : createdBy;
        
        jdbcTemplate.update(
            "INSERT INTO Cards (StageId, Title, CardDescription, CreatedAt, CreatedBy, " +
            "CardStatus, CardLocation, StartDate, DueDate, CardCoverTypeId, CoverValue, Position, " +
            "UpdatedAt, UpdatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            stageId, title, cardDescription, createdAt, createdBy, cardStatus, cardLocation,
            startDate, dueDate, cardCoverTypeId, coverValue, position, finalUpdatedAt, finalUpdatedBy);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Cards WHERE Id = LAST_INSERT_ID()",
            cardsRowMapper);
    }
    public Cards getCardById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Cards WHERE Id = ?",
            cardsRowMapper,
            id);
    }
    public List<Cards> getCardsByStage(int stageId) {
        return jdbcTemplate.query(
            "SELECT * FROM Cards WHERE StageId = ? ORDER BY Position",
            cardsRowMapper,
            stageId);
    }
    public List<Cards> getCardsByStatus(String cardStatus) {
        return jdbcTemplate.query(
            "SELECT * FROM Cards WHERE CardStatus = ? ORDER BY UpdatedAt DESC",
            cardsRowMapper,
            cardStatus);
    }
    public List<Cards> getCardsByDueDateRange(LocalDate start, LocalDate end) {
        return jdbcTemplate.query(
            "SELECT * FROM Cards WHERE DueDate BETWEEN ? AND ? ORDER BY DueDate",
            cardsRowMapper,
            start, end);
    }
    @Transactional
    public int updateCard(int id, String title, String cardDescription, String cardStatus,
            String cardLocation, LocalDate startDate, LocalDate dueDate, Integer cardCoverTypeId,
            String coverValue, Integer position, LocalDateTime updatedAt, int updatedBy) {
        
        if (title != null && title.isBlank()) {
            throw new IllegalArgumentException("Card title cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        // Get existing card to merge with updates
        Cards existingCard = getCardById(id);
        
        String finalTitle = title != null ? title : existingCard.getTitle();
        String finalDescription = cardDescription != null ? cardDescription : existingCard.getCardDescription();
        String finalStatus = cardStatus != null ? cardStatus : existingCard.getCardStatus();
        String finalLocation = cardLocation != null ? cardLocation : existingCard.getCardLocation();
        LocalDate finalStartDate = startDate != null ? startDate : existingCard.getStartDate();
        LocalDate finalDueDate = dueDate != null ? dueDate : existingCard.getDueDate();
        int finalCoverTypeId = cardCoverTypeId != null ? cardCoverTypeId : existingCard.getCardCoverTypeId();
        String finalCoverValue = coverValue != null ? coverValue : existingCard.getCoverValue();
        int finalPosition = position != null ? position : existingCard.getPosition();
        
        return jdbcTemplate.update(
            "UPDATE Cards SET Title = ?, CardDescription = ?, CardStatus = ?, " +
            "CardLocation = ?, StartDate = ?, DueDate = ?, CardCoverTypeId = ?, " +
            "CoverValue = ?, Position = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
            finalTitle, finalDescription, finalStatus, finalLocation, finalStartDate,
            finalDueDate, finalCoverTypeId, finalCoverValue, finalPosition,
            updatedAt, updatedBy, id);
    }
    @Transactional
    public int updateCardPosition(int id, int newPosition, LocalDateTime updatedAt, int updatedBy) {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        return jdbcTemplate.update(
            "UPDATE Cards SET Position = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
            newPosition, updatedAt, updatedBy, id);
    }
    @Transactional
    public int moveCardToStage(int id, int newStageId, int newPosition, LocalDateTime updatedAt, int updatedBy) {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        return jdbcTemplate.update(
            "UPDATE Cards SET StageId = ?, Position = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
            newStageId, newPosition, updatedAt, updatedBy, id);
    }
    public int deleteCard(int id) {
        return jdbcTemplate.update(
            "DELETE FROM Cards WHERE Id = ?",
            id);
    }
}
