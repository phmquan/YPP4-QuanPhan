package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.DataType;
import vn.ypp4.quanphan.service.mapper.row.DataTypeRowMapper;

@Service
@RequiredArgsConstructor
public class DataTypeServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final DataTypeRowMapper dataTypeRowMapper;

    public DataType createDataType(String dataTypeValue) {
        if (dataTypeValue == null || dataTypeValue.isBlank()) {
            throw new IllegalArgumentException("Data type value cannot be null or empty");
        }

        // Check if data type with this value already exists
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM DataType WHERE DataTypeValue = ?",
                Integer.class,
                dataTypeValue);

        if (count != null && count > 0) {
            throw new IllegalStateException("Data type with value '" + dataTypeValue + "' already exists");
        }

        jdbcTemplate.update(
                "INSERT INTO DataType (DataTypeValue) VALUES (?)",
                dataTypeValue);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM DataType WHERE Id = LAST_INSERT_ID()",
                dataTypeRowMapper);
    }

    public DataType getDataTypeById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM DataType WHERE Id = ?",
                    dataTypeRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public DataType getDataTypeByValue(String dataTypeValue) {
        if (dataTypeValue == null || dataTypeValue.isBlank()) {
            throw new IllegalArgumentException("Data type value cannot be null or empty");
        }

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM DataType WHERE DataTypeValue = ?",
                    dataTypeRowMapper,
                    dataTypeValue);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<DataType> getAllDataTypes() {
        return jdbcTemplate.query(
                "SELECT * FROM DataType ORDER BY Id",
                dataTypeRowMapper);
    }

    public int updateDataType(int id, String dataTypeValue) {
        if (dataTypeValue == null || dataTypeValue.isBlank()) {
            throw new IllegalArgumentException("Data type value cannot be null or empty");
        }

        // Check if another data type with this value already exists
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM DataType WHERE DataTypeValue = ? AND Id != ?",
                Integer.class,
                dataTypeValue, id);

        if (count != null && count > 0) {
            throw new IllegalStateException("Another data type with value '" + dataTypeValue + "' already exists");
        }

        return jdbcTemplate.update(
                "UPDATE DataType SET DataTypeValue = ? WHERE Id = ?",
                dataTypeValue, id);
    }

    public int deleteDataType(int id) {
        // First check if the data type is being used by any custom fields
        Integer usageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CustomField WHERE DataTypeId = ?",
                Integer.class,
                id);

        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException(
                    "Cannot delete data type as it is being used by " + usageCount + " custom field(s)");
        }

        return jdbcTemplate.update(
                "DELETE FROM DataType WHERE Id = ?",
                id);
    }
}
