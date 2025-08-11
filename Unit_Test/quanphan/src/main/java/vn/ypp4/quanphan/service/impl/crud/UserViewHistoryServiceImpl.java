package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.UserViewHistory;
import vn.ypp4.quanphan.service.mapper.row.UserViewHistoryRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserViewHistoryServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserViewHistoryRowMapper userViewHistoryRowMapper;

    public List<UserViewHistory> findAll() {
        String sql = "SELECT UserId, OwnerTypeId, OwnerId, AccessedAt FROM UserViewHistory";
        return jdbcTemplate.query(sql, userViewHistoryRowMapper);
    }

    public Optional<UserViewHistory> findByUserIdAndOwnerTypeIdAndOwnerId(int userId, int ownerTypeId, int ownerId) {
        String sql = "SELECT UserId, OwnerTypeId, OwnerId, AccessedAt FROM UserViewHistory WHERE UserId = ? AND OwnerTypeId = ? AND OwnerId = ?";
        List<UserViewHistory> userViewHistories = jdbcTemplate.query(sql, userViewHistoryRowMapper, userId, ownerTypeId,
                ownerId);
        return userViewHistories.isEmpty() ? Optional.empty() : Optional.of(userViewHistories.get(0));
    }

    public UserViewHistory save(UserViewHistory userViewHistory) {
        Optional<UserViewHistory> existing = findByUserIdAndOwnerTypeIdAndOwnerId(
                userViewHistory.getUserId(),
                userViewHistory.getOwnerTypeId(),
                userViewHistory.getOwnerId());

        if (existing.isPresent()) {
            return update(userViewHistory);
        } else {
            return create(userViewHistory);
        }
    }

    private UserViewHistory create(UserViewHistory userViewHistory) {
        String sql = "INSERT INTO UserViewHistory (UserId, OwnerTypeId, OwnerId, AccessedAt) VALUES (?, ?, ?, ?)";

        userViewHistory.setAccessedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                userViewHistory.getUserId(),
                userViewHistory.getOwnerTypeId(),
                userViewHistory.getOwnerId(),
                userViewHistory.getAccessedAt());

        return userViewHistory;
    }

    private UserViewHistory update(UserViewHistory userViewHistory) {
        String sql = "UPDATE UserViewHistory SET AccessedAt = ? WHERE UserId = ? AND OwnerTypeId = ? AND OwnerId = ?";

        userViewHistory.setAccessedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                userViewHistory.getAccessedAt(),
                userViewHistory.getUserId(),
                userViewHistory.getOwnerTypeId(),
                userViewHistory.getOwnerId());

        return userViewHistory;
    }

    public void deleteByUserIdAndOwnerTypeIdAndOwnerId(int userId, int ownerTypeId, int ownerId) {
        String sql = "DELETE FROM UserViewHistory WHERE UserId = ? AND OwnerTypeId = ? AND OwnerId = ?";
        jdbcTemplate.update(sql, userId, ownerTypeId, ownerId);
    }

    public List<UserViewHistory> findByUserId(int userId) {
        String sql = "SELECT UserId, OwnerTypeId, OwnerId, AccessedAt FROM UserViewHistory WHERE UserId = ? ORDER BY AccessedAt DESC";
        return jdbcTemplate.query(sql, userViewHistoryRowMapper, userId);
    }

    public List<UserViewHistory> findByUserIdAndOwnerTypeId(int userId, int ownerTypeId) {
        String sql = "SELECT UserId, OwnerTypeId, OwnerId, AccessedAt FROM UserViewHistory WHERE UserId = ? AND OwnerTypeId = ? ORDER BY AccessedAt DESC";
        return jdbcTemplate.query(sql, userViewHistoryRowMapper, userId, ownerTypeId);
    }

    public List<UserViewHistory> findRecentViewsByUserId(int userId, int limit) {
        String sql = "SELECT TOP(?) UserId, OwnerTypeId, OwnerId, AccessedAt FROM UserViewHistory WHERE UserId = ? ORDER BY AccessedAt DESC";
        return jdbcTemplate.query(sql, userViewHistoryRowMapper, limit, userId);
    }

    public void recordAccess(int userId, int ownerTypeId, int ownerId) {
        UserViewHistory viewHistory = new UserViewHistory();
        viewHistory.setUserId(userId);
        viewHistory.setOwnerTypeId(ownerTypeId);
        viewHistory.setOwnerId(ownerId);
        save(viewHistory);
    }
}
