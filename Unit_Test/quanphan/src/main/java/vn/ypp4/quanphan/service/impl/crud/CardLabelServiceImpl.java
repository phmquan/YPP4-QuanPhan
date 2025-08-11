package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.CardLabel;
import vn.ypp4.quanphan.service.mapper.row.CardLabelRowMapper;

@Service
@RequiredArgsConstructor
public class CardLabelServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CardLabelRowMapper cardLabelRowMapper;

    public void addLabelToCard(int cardId, int labelId) {
        if (isLabelOnCard(cardId, labelId)) {
            return; // Label already on card
        }

        jdbcTemplate.update(
                "INSERT INTO CardLabel (CardId, LabelId) VALUES (?, ?)",
                cardId, labelId);
    }

    public void removeLabelFromCard(int cardId, int labelId) {
        jdbcTemplate.update(
                "DELETE FROM CardLabel WHERE CardId = ? AND LabelId = ?",
                cardId, labelId);
    }

    public List<CardLabel> getLabelsForCard(int cardId) {
        return jdbcTemplate.query(
                "SELECT * FROM CardLabel WHERE CardId = ?",
                cardLabelRowMapper,
                cardId);
    }

    public List<CardLabel> getCardsWithLabel(int labelId) {
        return jdbcTemplate.query(
                "SELECT * FROM CardLabel WHERE LabelId = ?",
                cardLabelRowMapper,
                labelId);
    }

    public boolean isLabelOnCard(int cardId, int labelId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CardLabel WHERE CardId = ? AND LabelId = ?",
                Integer.class,
                cardId, labelId);
        return count != null && count > 0;
    }
}
