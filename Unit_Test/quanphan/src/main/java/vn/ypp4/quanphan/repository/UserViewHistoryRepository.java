package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.entity.Board;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserViewHistoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Board> findRecentlyViewedBoardsByUserId(int userId,int numBoardRequest){
        String sql = "SELECT b.Id, \n" +
                "  b.BoardName, \n" +
                "  b.BoardDescription, \n" +
                "  b.CreatedAt, \n" +
                "  b.CreatedBy, \n" +
                "  b.BackgroundUrl, \n" +
                "  b.WorkspaceId, \n" +
                "  b.BoardStatus, \n" +
                "  b.UpdatedAt, \n" +
                "  b.UpdatedBy \n" +
                "FROM Board b\n" +
                "JOIN UserViewHistory uvh ON uvh.OwnerId = b.Id\n" +
                "JOIN OwnerType owt ON owt.Id = uvh.OwnerTypeId AND owt.OwnerTypeValue = 'board'\n" +
                "JOIN Users u ON u.Id = uvh.UserId\n" +
                "WHERE u.Id = ?\n" +
                "ORDER BY uvh.AccessedAt DESC\n" +
                "LIMIT ?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Board.class)
                , userId,numBoardRequest);
    }
}
