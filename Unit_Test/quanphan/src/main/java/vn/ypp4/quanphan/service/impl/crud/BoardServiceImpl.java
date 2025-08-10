package vn.ypp4.quanphan.service.impl.crud;

import java.time.Instant;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.service.mapper.BoardRowMapper;
import vn.ypp4.quanphan.util.constant.BoardStatusEnum;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final BoardRowMapper boardRowMapper;
    public int archiveBoard(int id) {
        return jdbcTemplate.update("UPDATE Boards SET BoardStatus='archive' WHERE Id = ?",
                boardRowMapper, id);
    }
    public Board createBoard(String boardName, String boardDescription, Instant createdAt, int createdBy,
            String backgroundUrl, int workspaceId, BoardStatusEnum boardStatus) {
        // TODO Auto-generated method stub
        return null;
    }
    public int deleteBoardById(int id) {
        // TODO Auto-generated method stub
        return 0;
    }
    public List<Board> getAllBoard() {
        // TODO Auto-generated method stub
        return null;
    }
    public Board getBoardById(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Board> getBoardByStatus(String status) {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Board> getBoardByWorkspaceAndStatus(int WorkspaceId, String status) {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Board> getBoardByWorkspaceId(int WorkspaceId) {
        // TODO Auto-generated method stub
        return null;
    }
    public int restoreBoard(int id) {
        // TODO Auto-generated method stub
        return 0;
    }
    public int updateBoardById(int id, String username, String bio, String pictureUrl) {
        // TODO Auto-generated method stub
        return 0;
    }
    public int updateBoardStatus(int id, String status) {
        // TODO Auto-generated method stub
        return 0;
    }

}
