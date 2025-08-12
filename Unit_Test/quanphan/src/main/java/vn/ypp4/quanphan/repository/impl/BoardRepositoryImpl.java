package vn.ypp4.quanphan.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.repository.interf.BoardRepository;
import vn.ypp4.quanphan.service.mapper.row.BoardRowMapper;

import java.util.Optional;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
    private JdbcTemplate jdbcTemplate;
    private final BoardRowMapper boardRowMapper;
    @Override
    public int createBoard(Board createBoard) {
        String sql= "INSERT INTO BOARD"
                + "(BoardName,"
                + "BoardDescription,"
                + "BackgroundUrl)"
                + "VALUES "
                + "(?,?,?)";
        return jdbcTemplate.update(sql,createBoard.getBoardName(),createBoard.getBoardDescription(),createBoard.getBackgroundUrl());
    }

    @Override
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

    @Override
    public int updateBoard(Board updateBoard) {
        return 0;
    }

    @Override
    public int deleteBoard(Board updateBoard) {
        return 0;
    }
}
