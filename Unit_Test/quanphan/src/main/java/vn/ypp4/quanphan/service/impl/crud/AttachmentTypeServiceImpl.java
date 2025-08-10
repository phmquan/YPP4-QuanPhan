package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.AttachmentType;

import vn.ypp4.quanphan.service.mapper.AttachmentTypeRowMapper;

@Service
@RequiredArgsConstructor
public class AttachmentTypeServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final AttachmentTypeRowMapper attachmentTypeRowMapper;

    public AttachmentType createAttachmentType(String typeValue, String displayValue) {
        if (typeValue == null || typeValue.isBlank()) {
            throw new IllegalArgumentException("Type value cannot be null or empty");
        }
        if (displayValue == null || displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be null or empty");
        }

        jdbcTemplate.update(
                "INSERT INTO AttachmentType (TypeValue, DisplayValue) VALUES (?, ?)",
                typeValue, displayValue);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM AttachmentType WHERE Id = LAST_INSERT_ID()",
                attachmentTypeRowMapper);
    }

    public AttachmentType getAttachmentTypeById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM AttachmentType WHERE Id = ?",
                attachmentTypeRowMapper,
                id);
    }

    public AttachmentType getAttachmentTypeByValue(String typeValue) {
        if (typeValue == null || typeValue.isBlank()) {
            throw new IllegalArgumentException("Type value cannot be null or empty");
        }

        return jdbcTemplate.queryForObject(
                "SELECT * FROM AttachmentType WHERE TypeValue = ?",
                attachmentTypeRowMapper,
                typeValue);
    }

    public List<AttachmentType> getAllAttachmentTypes() {
        return jdbcTemplate.query(
                "SELECT * FROM AttachmentType ORDER BY Id",
                attachmentTypeRowMapper);
    }

    public int updateAttachmentType(int id, String typeValue, String displayValue) {
        if (typeValue == null || typeValue.isBlank()) {
            throw new IllegalArgumentException("Type value cannot be null or empty");
        }
        if (displayValue == null || displayValue.isBlank()) {
            throw new IllegalArgumentException("Display value cannot be null or empty");
        }

        return jdbcTemplate.update(
                "UPDATE AttachmentType SET TypeValue = ?, DisplayValue = ? WHERE Id = ?",
                typeValue, displayValue, id);
    }

    public int deleteAttachmentType(int id) {
        return jdbcTemplate.update(
                "DELETE FROM AttachmentType WHERE Id = ?",
                id);
    }
}
