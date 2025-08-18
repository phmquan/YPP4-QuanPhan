package vn.ypp4.quanphan.service.board;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ypp4.quanphan.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.entity.Board;

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
