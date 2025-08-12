package vn.ypp4.quanphan.repository.impl;

import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.repository.interf.UserFavoritedBoardRepository;
import vn.ypp4.quanphan.service.mapper.row.BoardRowMapper;

import java.util.List;

public class UserFavoritedBoardRepositoryImpl implements UserFavoritedBoardRepository {
    private JdbcTemplate jdbcTemplate;
    private BoardRowMapper boardRowMapper;

    @Override
    public List<Board> getFavoritedBoardsByUserId(int userId) {
        String sql="select \n" +
                "  b.Id, \n" +
                "  b.BoardName, \n" +
                "  b.BackgroundUrl \n" +
                "from \n" +
                "  UserStarredBoard usb \n" +
                "  join Board b on b.Id = usb.BoardId \n" +
                "where \n" +
                "  usb.UserId = ? \n" +
                "order by \n" +
                "  usb.CreatedAt desc\n";
        return jdbcTemplate.query(sql,boardRowMapper,userId);
    }
}
