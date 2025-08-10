package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.RolePermission;
import vn.ypp4.quanphan.service.mapper.RolePermissionRowMapper;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final RolePermissionRowMapper rolePermissionRowMapper;
    @Transactional
    public RolePermission createRolePermission(String permissionName, String permissionCode) {
        validatePermission(permissionName, permissionCode);
        
        // Check if permission code is unique (case-insensitive)
        Integer codeExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM RolePermission WHERE LOWER(PermissionCode) = LOWER(?)",
            Integer.class,
            permissionCode);
            
        if (codeExists != null && codeExists > 0) {
            throw new IllegalStateException("Permission with code '" + permissionCode + "' already exists");
        }
        
        jdbcTemplate.update(
            "INSERT INTO RolePermission (PermissionName, PermissionCode) VALUES (?, ?)",
            permissionName, permissionCode);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM RolePermission WHERE Id = LAST_INSERT_ID()",
            rolePermissionRowMapper);
    }
    public RolePermission getRolePermissionById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM RolePermission WHERE Id = ?",
                rolePermissionRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public RolePermission getRolePermissionByCode(String permissionCode) {
        if (permissionCode == null || permissionCode.isBlank()) {
            throw new IllegalArgumentException("Permission code cannot be null or empty");
        }
        
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM RolePermission WHERE LOWER(PermissionCode) = LOWER(?)",
                rolePermissionRowMapper,
                permissionCode);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<RolePermission> getAllRolePermissions() {
        return jdbcTemplate.query(
            "SELECT * FROM RolePermission ORDER BY Id",
            rolePermissionRowMapper);
    }
    @Transactional
    public int updateRolePermission(int id, String permissionName, String permissionCode) {
        // Get existing permission
        RolePermission existingPermission = getRolePermissionById(id);
        if (existingPermission == null) {
            throw new IllegalArgumentException("Role permission with ID " + id + " not found");
        }
        
        // Use existing values if not provided in the update
        String finalPermissionName = permissionName != null ? permissionName : existingPermission.getPermissionName();
        String finalPermissionCode = permissionCode != null ? permissionCode : existingPermission.getPermissionCode();
        
        // Validate the updated values
        validatePermission(finalPermissionName, finalPermissionCode);
        
        // Check if permission code is being changed and is unique (case-insensitive)
        if (permissionCode != null && !permissionCode.equalsIgnoreCase(existingPermission.getPermissionCode())) {
            Integer codeExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM RolePermission WHERE LOWER(PermissionCode) = LOWER(?) AND Id != ?",
                Integer.class,
                permissionCode, id);
                
            if (codeExists != null && codeExists > 0) {
                throw new IllegalStateException("Another permission with code '" + permissionCode + "' already exists");
            }
        }
        
        return jdbcTemplate.update(
            "UPDATE RolePermission SET PermissionName = ?, PermissionCode = ? WHERE Id = ?",
            finalPermissionName, finalPermissionCode, id);
    }
    @Transactional
    public int deleteRolePermission(int id) {
        // Check if permission exists
        RolePermission existingPermission = getRolePermissionById(id);
        if (existingPermission == null) {
            throw new IllegalArgumentException("Role permission with ID " + id + " not found");
        }
        
        // Check if permission is in use
        if (isPermissionInUse(id)) {
            throw new IllegalStateException("Cannot delete permission as it is being used by one or more members");
        }
        
        return jdbcTemplate.update(
            "DELETE FROM RolePermission WHERE Id = ?",
            id);
    }
    public boolean isPermissionInUse(int id) {
        // Check if any members reference this permission
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Members WHERE RolePermissionId = ?",
            Integer.class,
            id);
            
        return count != null && count > 0;
    }
    
    private void validatePermission(String permissionName, String permissionCode) {
        if (permissionName == null || permissionName.isBlank()) {
            throw new IllegalArgumentException("Permission name cannot be null or empty");
        }
        
        if (permissionName.length() > 100) {
            throw new IllegalArgumentException("Permission name cannot exceed 100 characters");
        }
        
        if (permissionCode == null || permissionCode.isBlank()) {
            throw new IllegalArgumentException("Permission code cannot be null or empty");
        }
        
        if (permissionCode.length() > 50) {
            throw new IllegalArgumentException("Permission code cannot exceed 50 characters");
        }
        
        // Ensure permission code is in uppercase with underscores (e.g., "ADMIN", "READ_ONLY")
        if (!permissionCode.matches("^[A-Z][A-Z0-9_]*$")) {
            throw new IllegalArgumentException("Permission code must be in uppercase and can only contain letters, numbers, and underscores");
        }
    }
}
