package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.CardSticker;
import vn.ypp4.quanphan.service.mapper.row.CardStickerRowMapper;

@Service
@RequiredArgsConstructor
public class CardStickerServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CardStickerRowMapper cardStickerRowMapper;

    public CardSticker addStickerToCard(int cardId, int stickerId, LocalDateTime createdAt,
            int createdBy, float positionX, float positionY, int indexZ) {
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        if (isStickerOnCard(cardId, stickerId)) {
            updateStickerPosition(cardId, stickerId, positionX, positionY, indexZ);
            return getStickerOnCard(cardId, stickerId);
        }

        jdbcTemplate.update(
                "INSERT INTO CardSticker (CardId, StickerId, CreatedAt, CreatedBy, PositionX, PositionY, IndexZ) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                cardId, stickerId, createdAt, createdBy, positionX, positionY, indexZ);

        return getStickerOnCard(cardId, stickerId);
    }

    public void updateStickerPosition(int cardId, int stickerId, float positionX, float positionY, int indexZ) {
        jdbcTemplate.update(
                "UPDATE CardSticker SET PositionX = ?, PositionY = ?, IndexZ = ? " +
                        "WHERE CardId = ? AND StickerId = ?",
                positionX, positionY, indexZ, cardId, stickerId);
    }

    public void removeStickerFromCard(int cardId, int stickerId) {
        jdbcTemplate.update(
                "DELETE FROM CardSticker WHERE CardId = ? AND StickerId = ?",
                cardId, stickerId);
    }

    public List<CardSticker> getStickersOnCard(int cardId) {
        return jdbcTemplate.query(
                "SELECT * FROM CardSticker WHERE CardId = ? ORDER BY IndexZ",
                cardStickerRowMapper,
                cardId);
    }

    public boolean isStickerOnCard(int cardId, int stickerId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CardSticker WHERE CardId = ? AND StickerId = ?",
                Integer.class,
                cardId, stickerId);
        return count != null && count > 0;
    }

    private CardSticker getStickerOnCard(int cardId, int stickerId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM CardSticker WHERE CardId = ? AND StickerId = ?",
                cardStickerRowMapper,
                cardId, stickerId);
    }
}
