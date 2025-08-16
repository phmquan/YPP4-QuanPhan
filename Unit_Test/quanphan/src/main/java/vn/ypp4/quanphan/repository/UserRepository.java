package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.domain.entity.User;


import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;



    public User findById(int userId) {
        String sql = "SELECT " +
                "Id, Username, FullName, Email, Bio, LastActive, CreatedAt, UpdatedAt" +
                " Avatar " +
                "FROM Users " +
                "WHERE Id = ?";

        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(User.class)
                , userId);
    }

    public boolean existsById(int userId) {
        String sql="SELECT COUNT(*) > 0 " +
                "FROM Users u " +
                "WHERE u.id = ?";
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
    }

    public int updateUserProfile(User updateUser) {
        String sql= "UPDATE Users \n"+
                "SET " +
                "Username = ?," +
                "Bio = ? \n"+
                "WHERE Id = ?";
        return jdbcTemplate.update(sql, updateUser.getUsername(),updateUser.getBio(),updateUser.getId());
    }
}
