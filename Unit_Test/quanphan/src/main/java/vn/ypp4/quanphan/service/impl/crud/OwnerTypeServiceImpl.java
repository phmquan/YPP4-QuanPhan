package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.OwnerType;
import vn.ypp4.quanphan.service.mapper.OwnerTypeRowMapper;

@Service
@RequiredArgsConstructor
public class OwnerTypeServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final OwnerTypeRowMapper ownerTypeRowMapper;
    @Transactional
    public OwnerType createOwnerType(String ownerTypeValue) {
        if (ownerTypeValue == null || ownerTypeValue.isBlank()) {
            throw new IllegalArgumentException("Owner type value cannot be null or empty");
        }
        
        // Check if owner type with this value already exists (case-insensitive)
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM OwnerType WHERE LOWER(OwnerTypeValue) = LOWER(?)",
            Integer.class,
            ownerTypeValue);
            
        if (count != null && count > 0) {
            throw new IllegalStateException("Owner type with value '" + ownerTypeValue + "' already exists");
        }
        
        jdbcTemplate.update(
            "INSERT INTO OwnerType (OwnerTypeValue) VALUES (?)",
            ownerTypeValue);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM OwnerType WHERE Id = LAST_INSERT_ID()",
            ownerTypeRowMapper);
    }
    public OwnerType getOwnerTypeById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM OwnerType WHERE Id = ?",
                ownerTypeRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public OwnerType getOwnerTypeByValue(String ownerTypeValue) {
        if (ownerTypeValue == null || ownerTypeValue.isBlank()) {
            throw new IllegalArgumentException("Owner type value cannot be null or empty");
        }
        
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM OwnerType WHERE LOWER(OwnerTypeValue) = LOWER(?)",
                ownerTypeRowMapper,
                ownerTypeValue);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<OwnerType> getAllOwnerTypes() {
        return jdbcTemplate.query(
            "SELECT * FROM OwnerType ORDER BY Id",
            ownerTypeRowMapper);
    }
    @Transactional
    public int updateOwnerType(int id, String ownerTypeValue) {
        if (ownerTypeValue == null || ownerTypeValue.isBlank()) {
            throw new IllegalArgumentException("Owner type value cannot be null or empty");
        }
        
        // Get existing owner type
        OwnerType existingType = getOwnerTypeById(id);
        if (existingType == null) {
            throw new IllegalArgumentException("Owner type with ID " + id + " not found");
        }
        
        // If the value is being changed, check for duplicates
        if (!ownerTypeValue.equalsIgnoreCase(existingType.getOwnerTypeValue())) {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM OwnerType WHERE LOWER(OwnerTypeValue) = LOWER(?) AND Id != ?",
                Integer.class,
                ownerTypeValue, id);
                
            if (count != null && count > 0) {
                throw new IllegalStateException("Another owner type with value '" + ownerTypeValue + "' already exists");
            }
        }
        
        return jdbcTemplate.update(
            "UPDATE OwnerType SET OwnerTypeValue = ? WHERE Id = ?",
            ownerTypeValue, id);
    }
    @Transactional
    public int deleteOwnerType(int id) {
        // Check if owner type is in use
        if (isOwnerTypeInUse(id)) {
            throw new IllegalStateException("Cannot delete owner type as it is being used by one or more members");
        }
        
        return jdbcTemplate.update(
            "DELETE FROM OwnerType WHERE Id = ?",
            id);
    }
    public boolean isOwnerTypeInUse(int id) {
        // Check if any members reference this owner type
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Members WHERE OwnerTypeId = ?",
            Integer.class,
            id);
            
        return count != null && count > 0;
    }
}
