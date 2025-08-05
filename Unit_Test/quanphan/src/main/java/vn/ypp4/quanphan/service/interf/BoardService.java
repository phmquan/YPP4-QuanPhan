package vn.ypp4.quanphan.service.interf;

import java.time.Instant;
import java.util.List;

import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.util.constant.BoardStatusEnum;

public interface BoardService {
    Board createBoard(String boardName, String boardDescription, Instant createdAt, int createdBy, String backgroundUrl,
            int workspaceId, BoardStatusEnum boardStatus);

    Board getBoardById(int id);

    Board getBoardByWorkspace(int WorkspaceId);

    Board getBoardByWorkspaceAndStatus(int WorkspaceId, String status);

    List<Board> getAllBoard();

    int updateBoardById(int id, String username, String bio, String pictureUrl);

    int deleteBoardById(int id);
}
