package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.User;
import vn.ypp4.quanphan.service.mapper.row.UserRowMapper;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public User findUserByUserId(int userId) {
        String sql="SELECT \n" +
                "\ts.Id\n" +
                "FROM Users s\n" +
                "WHERE s.Id=?";
        return jdbcTemplate.queryForObject(sql,userRowMapper,userId);
    }


    public boolean existsById(int userId) {
        String sql="SELECT \n" +
                "\ts.Id\n" +
                "FROM Users s\n" +
                "WHERE s.Id=?";
        User requestedUser= jdbcTemplate.queryForObject(sql,userRowMapper,userId);
        return requestedUser != null;
    }


    public void createUser(User testUser) {
        String sql="INSERT INTO USERS ()";
    }
}
