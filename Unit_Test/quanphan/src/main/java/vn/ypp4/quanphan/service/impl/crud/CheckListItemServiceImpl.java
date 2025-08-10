package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.CheckListItem;
import vn.ypp4.quanphan.service.mapper.CheckListItemRowMapper;

@Service
@RequiredArgsConstructor
public class CheckListItemServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CheckListItemRowMapper checkListItemRowMapper;
    @Transactional
    public CheckListItem createCheckListItem(String checkListItemName, int checkListId, int memberId,
            LocalDate dueDate, boolean checkListItemStatus, LocalDateTime createdAt, int createdBy, int position) {
        
        if (checkListItemName == null || checkListItemName.isBlank()) {
            throw new IllegalArgumentException("Checklist item name cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        
        // Set updatedAt to createdAt if not provided
        LocalDateTime updatedAt = createdAt;
        int updatedBy = createdBy;
        
        jdbcTemplate.update(
            "INSERT INTO CheckListItem (CheckListItemName, CheckListId, MemberId, DueDate, " +
            "CheckListItemStatus, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, Position) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            checkListItemName, checkListId, memberId, dueDate, checkListItemStatus,
            createdAt, createdBy, updatedAt, updatedBy, position);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM CheckListItem WHERE Id = LAST_INSERT_ID()",
            checkListItemRowMapper);
    }
    public CheckListItem getCheckListItemById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM CheckListItem WHERE Id = ?",
            checkListItemRowMapper,
            id);
    }
    public List<CheckListItem> getCheckListItemsByCheckList(int checkListId) {
        return jdbcTemplate.query(
            "SELECT * FROM CheckListItem WHERE CheckListId = ? ORDER BY Position",
            checkListItemRowMapper,
            checkListId);
    }
    public List<CheckListItem> getCheckListItemsByMember(int memberId) {
        return jdbcTemplate.query(
            "SELECT * FROM CheckListItem WHERE MemberId = ? ORDER BY DueDate, Position",
            checkListItemRowMapper,
            memberId);
    }
    @Transactional
    public int updateCheckListItem(int id, String checkListItemName, Integer memberId, 
            LocalDate dueDate, Boolean checkListItemStatus, LocalDateTime updatedAt, int updatedBy) {
        
        if (checkListItemName != null && checkListItemName.isBlank()) {
            throw new IllegalArgumentException("Checklist item name cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        // Get existing checklist item to merge with updates
        CheckListItem existingItem = getCheckListItemById(id);
        
        String finalName = checkListItemName != null ? checkListItemName : existingItem.getCheckListItemName();
        int finalMemberId = memberId != null ? memberId : existingItem.getMemberId();
        LocalDate finalDueDate = dueDate != null ? dueDate : existingItem.getDueDate();
        boolean finalStatus = checkListItemStatus != null ? checkListItemStatus : existingItem.isCheckListItemStatus();
        
        return jdbcTemplate.update(
            "UPDATE CheckListItem SET CheckListItemName = ?, MemberId = ?, " +
            "DueDate = ?, CheckListItemStatus = ?, UpdatedAt = ?, UpdatedBy = ? " +
            "WHERE Id = ?",
            finalName, finalMemberId, finalDueDate, finalStatus, updatedAt, updatedBy, id);
    }
    @Transactional
    public int toggleCheckListItemStatus(int id, boolean newStatus, LocalDateTime updatedAt, int updatedBy) {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        return jdbcTemplate.update(
            "UPDATE CheckListItem SET CheckListItemStatus = ?, UpdatedAt = ?, UpdatedBy = ? " +
            "WHERE Id = ?",
            newStatus, updatedAt, updatedBy, id);
    }
    @Transactional
    public int updateCheckListItemPosition(int id, int newPosition, LocalDateTime updatedAt, int updatedBy) {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        
        return jdbcTemplate.update(
            "UPDATE CheckListItem SET Position = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
            newPosition, updatedAt, updatedBy, id);
    }
    public int deleteCheckListItem(int id) {
        return jdbcTemplate.update(
            "DELETE FROM CheckListItem WHERE Id = ?",
            id);
    }
}
