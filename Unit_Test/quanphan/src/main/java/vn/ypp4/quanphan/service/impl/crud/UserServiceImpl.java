package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.User;
import vn.ypp4.quanphan.service.mapper.UserRowMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public User createUser(String username, String bio, String email, LocalDateTime lastActive, LocalDateTime createdAt,
            String pictureUrl) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty for user");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        jdbcTemplate.update(
                "INSERT INTO Users (Username, Bio, Email, LastActive, CreatedAt, UpdatedAt, PictureUrl) VALUES (?, ?, ?, ?, ?, ?, ?)",
                username, bio, email, lastActive, createdAt, createdAt, pictureUrl);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Users WHERE Id = LAST_INSERT_ID()",
                userRowMapper);
    }

    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Users WHERE Email = ?",
                userRowMapper,
                email);
    }

    public List<User> getAllUser() {
        return jdbcTemplate.query("SELECT * FROM Users ORDER BY Username", userRowMapper);
    }

    public int deleteUserById(int id) {
        return jdbcTemplate.update("DELETE FROM Users WHERE Id = ?", id);
    }

    public User getUserById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Users WHERE Id = ?", userRowMapper, id);
    }

    public int updateUserById(int id, String username, String bio, String pictureUrl) {
        User currentUser = getUserById(id);

        String finalUsername = (username != null && !username.isBlank()) ? username : currentUser.getUsername();
        String finalBio = (bio != null && !bio.isBlank()) ? bio : currentUser.getBio();
        String finalPictureUrl = (pictureUrl != null && !pictureUrl.isBlank()) ? pictureUrl
                : currentUser.getPictureUrl();

        return jdbcTemplate.update(
                "UPDATE Users SET Username = ?, Bio = ?, PictureUrl = ?, UpdatedAt = ? WHERE Id = ?",
                finalUsername, finalBio, finalPictureUrl, LocalDateTime.now(), id);
    }

}
