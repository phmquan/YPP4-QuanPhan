package vn.ypp4.quanphan.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.ypp4.quanphan.domain.User;
import vn.ypp4.quanphan.repository.interf.UserRepository;
import vn.ypp4.quanphan.service.mapper.row.UserRowMapper;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;
    @Override
    public User findUserByUserId(int userId) {
        String sql="SELECT \n" +
                "\ts.Id\n" +
                "FROM Users s\n" +
                "WHERE s.Id=?";
        return jdbcTemplate.queryForObject(sql,userRowMapper,userId);
    }

    @Override
    public boolean existsById(int userId) {
        String sql="SELECT \n" +
                "\ts.Id\n" +
                "FROM Users s\n" +
                "WHERE s.Id=?";
        User requestedUser= jdbcTemplate.queryForObject(sql,userRowMapper,userId);
        return requestedUser != null;
    }

    @Override
    public void createUser(User testUser) {
        String sql="INSERT INTO USERS ()"
    }
}
