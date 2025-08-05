package vn.ypp4.quanphan.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.User;
import vn.ypp4.quanphan.service.interf.UserService;
import vn.ypp4.quanphan.service.mapper.UserRowMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public User createUser(String Username, String Bio, String Email, Instant LastActive, Instant CreatedAt,
            String PictureUrl) {
        if (Username == null) {
            throw new IllegalArgumentException("Username cannot be null or empty for user");
        }
        if (Email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (CreatedAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        jdbcTemplate.update(
                "INSERT INTO Users (Username,Bio,Email,LastActive,CreatedAt,PictureUrl) VALUES (?,?,?,?,?,?)",
                Username, Bio, Email, java.sql.Timestamp.from(LastActive), java.sql.Timestamp.from(CreatedAt),
                PictureUrl);
        return getUserByEmail(Email);
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT Username,Email FROM Users WHERE Email = ?",
                userRowMapper,
                email);
    }

    @Override
    public List<User> getAllUser() {
        return jdbcTemplate.query("SELECT Username,Email From Users", userRowMapper);
    }

    @Override
    public int deleteUserById(int id) {
        return jdbcTemplate.update("DELETE FROM Users WHERE Id=?", userRowMapper, id);
    }

    @Override
    public User getUserById(int id) {

        return jdbcTemplate.queryForObject("SELECT Username,Email FROM Users Where Id=?", userRowMapper, id);
    }

    @Override
    public int updateUserById(int id, String username, String bio, String pictureUrl) {
        User currentUser = getUserById(id);
        currentUser.setUsername(username.isBlank() ? currentUser.getUsername() : username);
        currentUser.setBio(bio.isBlank() ? currentUser.getBio() : bio);
        currentUser.setPictureUrl(pictureUrl.isBlank() ? currentUser.getPictureUrl() : pictureUrl);
        return jdbcTemplate.update(
                "UPDATE Users Set Username = ?, Bio = ?, PictureUrl=?  WHERE Id=?",
                userRowMapper,
                currentUser.getUsername(), currentUser.getBio(), currentUser.getPictureUrl(), currentUser.getId());
    }

}
