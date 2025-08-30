package vn.ypp4.quanphan.api.service.board;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;

import java.util.List;

@Service
public interface BoardService {
    ResponseEntity<List<BoardResponseDTO>> getStarredBoardsByUserId(@RequestParam int userId);

    ResponseEntity<List<BoardResponseDTO>> getViewedBoardsByUserId(@RequestParam int userId);

    ResponseEntity<BoardResponseDTO> getBoardById(@RequestParam int boardId);

    ResponseEntity<List<BoardResponseDTO>> getStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId);

    ResponseEntity<List<BoardResponseDTO>> getMemberBoardsByUserId(int userId);

    ResponseEntity<List<BoardResponseDTO>> getMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId);
}
