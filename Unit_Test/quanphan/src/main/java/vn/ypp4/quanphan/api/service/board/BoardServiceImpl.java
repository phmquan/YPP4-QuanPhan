package vn.ypp4.quanphan.api.service.board;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.api.repository.board.UserBoardRepository;

import java.util.List;

@Service
@NoArgsConstructor
public class BoardServiceImpl implements BoardService {
    @Autowired
    private UserBoardRepository userBoardRepository;

    @Override
    public ResponseEntity<List<BoardResponseDTO>> getStarredBoardsByUserId(int userId) {
        try {
            List<BoardResponseDTO> boards = userBoardRepository.findStarredBoardsByUserId(userId);
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<BoardResponseDTO>> getViewedBoardsByUserId(int userId) {
        try {
            List<BoardResponseDTO> boards = userBoardRepository.findViewedBoardsByUserId(userId);
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<BoardResponseDTO> getBoardById(int boardId) {
        try {
            BoardResponseDTO board = userBoardRepository.findById(boardId);
            if (board != null) {
                return ResponseEntity.ok(board);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<BoardResponseDTO>> getStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        try {
            List<BoardResponseDTO> boards = userBoardRepository.findStarredBoardsByUserIdAndWorkspaceId(userId,
                    workspaceId);
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<BoardResponseDTO>> getMemberBoardsByUserId(int userId) {
        try {
            List<BoardResponseDTO> boards = userBoardRepository.findMemberBoardsByUserId(userId);
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<BoardResponseDTO>> getMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        try {
            List<BoardResponseDTO> boards = userBoardRepository.findMemberBoardsByUserIdAndWorkspaceId(userId,
                    workspaceId);
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
