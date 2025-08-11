package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.CardCoverType;
import vn.ypp4.quanphan.service.mapper.row.CardCoverTypeRowMapper;

@Service
@RequiredArgsConstructor
public class CardCoverTypeServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CardCoverTypeRowMapper cardCoverTypeRowMapper;

    public CardCoverType createCardCoverType(String typeValue, String displayValue) {
        if (typeValue == null || typeValue.isBlank()) {
            throw new IllegalArgumentException("Type value cannot be null or empty");
        }
        if (displayValue == null || displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be null or empty");
        }

        jdbcTemplate.update(
                "INSERT INTO CardCoverType (TypeValue, DisplayValue) VALUES (?, ?)",
                typeValue, displayValue);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM CardCoverType WHERE Id = LAST_INSERT_ID()",
                cardCoverTypeRowMapper);
    }

    public CardCoverType getCardCoverTypeById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM CardCoverType WHERE Id = ?",
                cardCoverTypeRowMapper,
                id);
    }

    public CardCoverType getCardCoverTypeByValue(String typeValue) {
        if (typeValue == null || typeValue.isBlank()) {
            throw new IllegalArgumentException("Type value cannot be null or empty");
        }

        return jdbcTemplate.queryForObject(
                "SELECT * FROM CardCoverType WHERE TypeValue = ?",
                cardCoverTypeRowMapper,
                typeValue);
    }

    public List<CardCoverType> getAllCardCoverTypes() {
        return jdbcTemplate.query(
                "SELECT * FROM CardCoverType ORDER BY Id",
                cardCoverTypeRowMapper);
    }

    public int updateCardCoverType(int id, String typeValue, String displayValue) {
        if (typeValue == null || typeValue.isBlank()) {
            throw new IllegalArgumentException("Type value cannot be null or empty");
        }
        if (displayValue == null || displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be null or empty");
        }

        return jdbcTemplate.update(
                "UPDATE CardCoverType SET TypeValue = ?, DisplayValue = ? WHERE Id = ?",
                typeValue, displayValue, id);
    }

    public int deleteCardCoverType(int id) {
        return jdbcTemplate.update(
                "DELETE FROM CardCoverType WHERE Id = ?",
                id);
    }
}
