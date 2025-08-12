package vn.ypp4.quanphan.repository.interf;

import vn.ypp4.quanphan.domain.Board;

import java.util.Optional;

public interface BoardRepository {
    int createBoard(Board createBoard);
    Board getBoardById(int boardId);
    int updateBoard(Board updateBoard);
    int deleteBoard(Board updateBoard);
}
