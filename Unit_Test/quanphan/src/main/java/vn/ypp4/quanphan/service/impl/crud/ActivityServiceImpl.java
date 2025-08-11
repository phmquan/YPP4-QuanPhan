package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Activity;
import vn.ypp4.quanphan.service.mapper.row.ActivityRowMapper;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl {
        private final JdbcTemplate jdbcTemplate;
        private final ActivityRowMapper activityRowMapper;

        public Activity createActivity(LocalDateTime createdAt, String activityDescription, int userId, int categoryId,
                        int ownerId) {
                if (createdAt == null) {
                        throw new IllegalArgumentException("CreatedAt cannot be null");
                }

                jdbcTemplate.update(
                                "INSERT INTO Activity (CreatedAt, ActivityDescription, UserId, CategoryId, OwnerId) " +
                                                "VALUES (?, ?, ?, ?, ?)",
                                createdAt, activityDescription, userId, categoryId, ownerId);

                return jdbcTemplate.queryForObject(
                                "SELECT * FROM Activity WHERE Id = LAST_INSERT_ID()",
                                activityRowMapper);
        }

        public Activity getActivityById(int id) {
                return jdbcTemplate.queryForObject(
                                "SELECT * FROM Activity WHERE Id = ?",
                                activityRowMapper,
                                id);
        }

        public List<Activity> getActivitiesByOwner(int ownerId) {
                return jdbcTemplate.query(
                                "SELECT * FROM Activity WHERE OwnerId = ? ORDER BY CreatedAt DESC",
                                activityRowMapper,
                                ownerId);
        }

        public List<Activity> getActivitiesByUser(int userId) {
                return jdbcTemplate.query(
                                "SELECT * FROM Activity WHERE UserId = ? ORDER BY CreatedAt DESC",
                                activityRowMapper,
                                userId);
        }

        public int updateActivity(int id, String activityDescription) {
                return jdbcTemplate.update(
                                "UPDATE Activity SET ActivityDescription = ? WHERE Id = ?",
                                activityDescription, id);
        }

        public int deleteActivity(int id) {
                return jdbcTemplate.update(
                                "DELETE FROM Activity WHERE Id = ?",
                                id);
        }
}
