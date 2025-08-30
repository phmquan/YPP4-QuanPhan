package vn.ypp4.quanphan.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.api.service.board.BoardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")

public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/starred")
    public ResponseEntity<List<BoardResponseDTO>> getStarredBoards(@RequestParam int userId) {
        return boardService.getStarredBoardsByUserId(userId);
    }

    @GetMapping("/viewed")
    public ResponseEntity<List<BoardResponseDTO>> getHistoryViewedBoards(@RequestParam int userId,
            @RequestParam int requestNumBoard) {
        return boardService.getViewedBoardsByUserId(userId);
    }

    @GetMapping
    public ResponseEntity<BoardResponseDTO> getBoardById(@RequestParam int boardId) {
        return boardService.getBoardById(boardId);
    }

    @GetMapping("/starred/workspace")
    public ResponseEntity<List<BoardResponseDTO>> getStarredBoardsByUserAndWorkspace(@RequestParam int userId,
            @RequestParam int workspaceId) {
        return boardService.getStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @GetMapping("/members/users")
    public ResponseEntity<List<BoardResponseDTO>> getMemberBoardsByUserId(@RequestParam int userId) {
        return boardService.getMemberBoardsByUserId(userId);
    }

    @GetMapping("/members/users/workspace")
    public ResponseEntity<List<BoardResponseDTO>> getMemberBoardsByUserIdAndWorkspaceId(@RequestParam int userId,
            @RequestParam int workspaceId) {
        return boardService.getMemberBoardsByUserIdAndWorkspaceId(userId, workspaceId);
    }
}
