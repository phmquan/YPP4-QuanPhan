package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.service.mapper.row.BoardRowMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private JdbcTemplate jdbcTemplate;
    private final BoardRowMapper boardRowMapper;
    
    public int createBoard(Board createBoard) {
        String sql= "INSERT INTO BOARD"
                + "(BoardName,"
                + "BoardDescription,"
                + "BackgroundUrl)"
                + "VALUES "
                + "(?,?,?)";
        return jdbcTemplate.update(sql,createBoard.getBoardName(),createBoard.getBoardDescription(),createBoard.getBackgroundUrl());
    }

    
    public Board getBoardById(int boardId) {
        String sql= "SELECT"
                + "b.Id"
                + "b.BoardName,"
                + "b.BackgroundUrl,"
                + "FROM Board b"
                + "WHERE"
                + "b.Id=?";
        return jdbcTemplate.queryForObject(sql,boardRowMapper,boardId);
    }


    public int updateBoard(Board updateBoard) {
        return 0;
    }


    public int deleteBoard(Board updateBoard) {
        return 0;
    }
}
