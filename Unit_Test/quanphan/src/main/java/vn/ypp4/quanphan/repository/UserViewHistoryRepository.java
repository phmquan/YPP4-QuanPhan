package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.service.mapper.row.BoardRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserViewHistoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BoardRowMapper boardRowMapper;
    public List<Board> findRecentlyViewedBoardByUserId(int userId,int numBoardRequested){
        String sql="SELECT TOP ? \n" +
                "    b.Id as BoardId,\n" +
                "    b.BoardName,\n" +
                "    b.BackgroundUrl\n" +
                "FROM Board b\n" +
                "    JOIN UserViewHistory uvh ON uvh.OwnerId  = b.Id\n" +
                "    JOIN OwnerType owt on owt.Id=uvh.OwnerTypeId and owt.OwnerTypeValue='board'\n" +
                "    JOIN Users u ON u.Id = uvh.UserId\n" +
                "WHERE u.Id = ? -- userId\n" +
                "ORDER BY uvh.AccessedAt DESC;\n";
        return jdbcTemplate.query(sql,boardRowMapper, numBoardRequested, userId);
    }
}
