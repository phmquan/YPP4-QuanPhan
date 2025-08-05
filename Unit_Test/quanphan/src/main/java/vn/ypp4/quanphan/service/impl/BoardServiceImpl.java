package vn.ypp4.quanphan.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.service.interf.BoardService;
import vn.ypp4.quanphan.service.mapper.BoardRowMapper;
import vn.ypp4.quanphan.util.constant.BoardStatusEnum;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final JdbcTemplate jdbcTemplate;
    private final BoardRowMapper boardRowMapper;

    @Override
    public int archiveBoard(int id) {
        return jdbcTemplate.update("UPDATE Boards SET BoardStatus='archive' WHERE Id = ?",
                boardRowMapper, id);
    }

    @Override
    public Board createBoard(String boardName, String boardDescription, Instant createdAt, int createdBy,
            String backgroundUrl, int workspaceId, BoardStatusEnum boardStatus) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int deleteBoardById(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Board> getAllBoard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Board getBoardById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Board> getBoardByStatus(String status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Board> getBoardByWorkspaceAndStatus(int WorkspaceId, String status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Board> getBoardByWorkspaceId(int WorkspaceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int restoreBoard(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateBoardById(int id, String username, String bio, String pictureUrl) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateBoardStatus(int id, String status) {
        // TODO Auto-generated method stub
        return 0;
    }

}
