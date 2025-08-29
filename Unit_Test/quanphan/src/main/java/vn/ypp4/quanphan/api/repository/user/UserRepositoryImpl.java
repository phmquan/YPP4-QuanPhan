package vn.ypp4.quanphan.api.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.api.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.api.dto.user.UserUpdateDTO;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   @Override
    public UserResponseDTO findById(int userId) {
        String sql=
                """
                    SELECT\s
                      u.Id,\s
                      u.Username,\s
                      u.FullName,\s
                      u.Email,\s
                      u.Avatar\s
                    FROM\s
                      Users u\s
                    WHERE\s
                      Id = ?
                """;
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(UserResponseDTO.class),
                userId
                );
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public int update(UserUpdateDTO userUpdate) {
       String sql="UPDATE Users SET Username=?, Bio=? WHERE Id=?";
       return jdbcTemplate.update(sql,userUpdate.getUsername(),userUpdate.getBio(),userUpdate.getId());
    }
}
