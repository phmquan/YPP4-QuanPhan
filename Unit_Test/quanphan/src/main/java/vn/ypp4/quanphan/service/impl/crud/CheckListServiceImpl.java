package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.CheckList;
import vn.ypp4.quanphan.service.mapper.row.CheckListRowMapper;

@Service
@RequiredArgsConstructor
public class CheckListServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CheckListRowMapper checkListRowMapper;

    @Transactional
    public CheckList createCheckList(String checkListName, int cardId, int position,
            LocalDateTime createdAt, int createdBy) {

        if (checkListName == null || checkListName.isBlank()) {
            throw new IllegalArgumentException("Checklist name cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        // Set updatedAt to createdAt if not provided
        LocalDateTime updatedAt = createdAt;
        int updatedBy = createdBy;

        jdbcTemplate.update(
                "INSERT INTO CheckList (CheckListName, CardId, Position, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                checkListName, cardId, position, createdAt, createdBy, updatedAt, updatedBy);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM CheckList WHERE Id = LAST_INSERT_ID()",
                checkListRowMapper);
    }

    public CheckList getCheckListById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM CheckList WHERE Id = ?",
                checkListRowMapper,
                id);
    }

    public List<CheckList> getCheckListsByCard(int cardId) {
        return jdbcTemplate.query(
                "SELECT * FROM CheckList WHERE CardId = ? ORDER BY Position",
                checkListRowMapper,
                cardId);
    }

    @Transactional
    public int updateCheckList(int id, String checkListName, Integer position,
            LocalDateTime updatedAt, int updatedBy) {

        if (checkListName != null && checkListName.isBlank()) {
            throw new IllegalArgumentException("Checklist name cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }

        // Get existing checklist to merge with updates
        CheckList existingCheckList = getCheckListById(id);

        String finalName = checkListName != null ? checkListName : existingCheckList.getCheckListName();
        int finalPosition = position != null ? position : existingCheckList.getPosition();

        return jdbcTemplate.update(
                "UPDATE CheckList SET CheckListName = ?, Position = ?, " +
                        "UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
                finalName, finalPosition, updatedAt, updatedBy, id);
    }

    @Transactional
    public int updateCheckListPosition(int id, int newPosition, LocalDateTime updatedAt, int updatedBy) {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }

        return jdbcTemplate.update(
                "UPDATE CheckList SET Position = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
                newPosition, updatedAt, updatedBy, id);
    }

    @Transactional
    public int deleteCheckList(int id) {
        // First delete all checklist items
        jdbcTemplate.update(
                "DELETE FROM CheckListItem WHERE CheckListId = ?",
                id);

        // Then delete the checklist itself
        return jdbcTemplate.update(
                "DELETE FROM CheckList WHERE Id = ?",
                id);
    }
}
