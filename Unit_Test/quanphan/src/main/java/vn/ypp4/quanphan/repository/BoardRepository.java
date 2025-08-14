package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;

import vn.ypp4.quanphan.domain.entity.Board;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    
    public int createBoard(BoardCreateDTO createBoard) {
        String sql= "INSERT INTO BOARD"
                + "(BoardName,"
                + "WorkspaceId,"
                + "BackgroundUrl)"
                + "VALUES "
                + "(?,?,?)";
        return jdbcTemplate.update(sql,createBoard.getBoardName(),createBoard.getWorkspaceId(),createBoard.getBackgroundUrl());
    }

    
    public Board getBoardById(int boardId) {
        String sql= "SELECT \n"
                + "b.Id, \n"
                + "b.BoardName, \n"
                + "b.BackgroundUrl, \n"
                + "b.BoardDescription, \n"
                + "b.CreatedAt, \n"
                + "b.CreatedBy, \n"
                + "b.BoardStatus, \n"
                + "b.UpdatedAt, \n"
                + "b.UpdatedBy \n"
                + "FROM Board b\n"
                + "WHERE \n"
                + "b.Id=?";
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(Board.class)
                ,boardId);
    }


    public int updateBoard(Board updateBoard) {
        return 0;
    }


    public int deleteBoard(Board updateBoard) {
        return 0;
    }
    public List<Board> getMemberBoardsByUserId(int userId){
        String sql="SELECT " +
                "    b.Id AS BoardId," +
                "    b.BoardName AS BoardName," +
                "    b.BoardDescription," +
                "    b.BackgroundUrl," +
                "    b.CreatedAt,\n" +
                "    b.CreatedBy,\n"+
                "    b.WorkspaceId as WorkspaceId,\n"+
                "    b.BoardStatus,\n"+
                "    b.UpdatedAt,\n"+
                "    b.UpdatedBy,\n"+
                "FROM Board b " +
                "    JOIN Members mb ON mb.OwnerId = b.Id\n" +
                "    JOIN OwnerType otb ON otb.Id = mb.OwnerTypeId \n" +
                "        AND otb.OwnerTypeValue = 'board'\n" +
                "WHERE mb.UserId = ?\n";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Board.class)
                ,userId);
    }
}
