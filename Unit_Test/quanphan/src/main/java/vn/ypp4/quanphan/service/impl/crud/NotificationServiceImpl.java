package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Notification;
import vn.ypp4.quanphan.service.mapper.NotificationRowMapper;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final NotificationRowMapper notificationRowMapper;
    @Transactional
    public Notification createNotification(int activityId, boolean isRead) {
        // Check if activity exists
        Integer activityExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Activity WHERE Id = ?",
            Integer.class,
            activityId);
            
        if (activityExists == null || activityExists == 0) {
            throw new IllegalArgumentException("Activity with ID " + activityId + " not found");
        }
        
        jdbcTemplate.update(
            "INSERT INTO Notification (ActivityId, IsRead) VALUES (?, ?)",
            activityId, isRead);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Notification WHERE Id = LAST_INSERT_ID()",
            notificationRowMapper);
    }
    public Notification getNotificationById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM Notification WHERE Id = ?",
                notificationRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Notification> getNotificationsByActivity(int activityId) {
        return jdbcTemplate.query(
            "SELECT * FROM Notification WHERE ActivityId = ?",
            notificationRowMapper,
            activityId);
    }
    public List<Notification> getUnreadNotifications() {
        return jdbcTemplate.query(
            "SELECT n.* FROM Notification n " +
            "INNER JOIN Activity a ON n.ActivityId = a.Id " +
            "WHERE n.IsRead = false " +
            "ORDER BY a.CreatedAt DESC",
            notificationRowMapper);
    }
    public List<Notification> getNotificationsByUser(int userId) {
        return jdbcTemplate.query(
            "SELECT n.* FROM Notification n " +
            "INNER JOIN Activity a ON n.ActivityId = a.Id " +
            "WHERE a.UserId = ? " +
            "ORDER BY a.CreatedAt DESC",
            notificationRowMapper,
            userId);
    }
    @Transactional
    public int markAsRead(int id) {
        // Check if notification exists
        Notification notification = getNotificationById(id);
        if (notification == null) {
            throw new IllegalArgumentException("Notification with ID " + id + " not found");
        }
        
        // Only update if not already read
        if (!notification.isRead()) {
            return jdbcTemplate.update(
                "UPDATE Notification SET IsRead = true WHERE Id = ?",
                id);
        }
        
        return 0; // No update needed
    }
    @Transactional
    public int markAllAsRead(int userId) {
        return jdbcTemplate.update(
            "UPDATE Notification n " +
            "INNER JOIN Activity a ON n.ActivityId = a.Id " +
            "SET n.IsRead = true " +
            "WHERE a.UserId = ? AND n.IsRead = false",
            userId);
    }
    @Transactional
    public int deleteNotification(int id) {
        return jdbcTemplate.update(
            "DELETE FROM Notification WHERE Id = ?",
            id);
    }
    @Transactional
    public int deleteOldNotifications(int daysToKeep) {
        if (daysToKeep < 0) {
            throw new IllegalArgumentException("Days to keep must be non-negative");
        }
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        
        return jdbcTemplate.update(
            "DELETE n FROM Notification n " +
            "INNER JOIN Activity a ON n.ActivityId = a.Id " +
            "WHERE a.CreatedAt < ?",
            cutoffDate);
    }
    public int getUnreadCount(int userId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Notification n " +
            "INNER JOIN Activity a ON n.ActivityId = a.Id " +
            "WHERE a.UserId = ? AND n.IsRead = false",
            Integer.class,
            userId);
            
        return count != null ? count : 0;
    }
}
