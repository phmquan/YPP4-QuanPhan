package vn.ypp4.quanphan.api.service.board;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;

import java.util.List;

@Service
public interface BoardService {
    List<BoardResponseDTO> getStarredBoardsByUserId(@RequestParam int userId);
    List<BoardResponseDTO> getViewedBoardsByUserId(@RequestParam int userId);

    BoardResponseDTO getBoardById(@RequestParam int boardId);
    List<BoardResponseDTO> getStarredBoardsByUserIdAndWorkspaceId( int userId, int workspaceId);

    List<BoardResponseDTO> getMemberBoardsByUserId(int userId);

    List<BoardResponseDTO> getMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId);
}
