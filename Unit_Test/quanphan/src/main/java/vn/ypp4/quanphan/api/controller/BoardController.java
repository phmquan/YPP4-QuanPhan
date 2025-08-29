package vn.ypp4.quanphan.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.api.service.board.BoardService;


import java.util.List;


@RestController
@RequestMapping("/api/v1/boards")

public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/starred/{id}")
    public List<BoardResponseDTO> getStarredBoards(@RequestParam int userId) {
        return boardService.getStarredBoardsByUserId(userId);
    }
    @GetMapping("/viewed/{id}")
    public List<BoardResponseDTO> getHistoryViewedBoards(@RequestParam int userId,@RequestParam int requestNumBoard){
        return boardService.getViewedBoardsByUserId(userId);
    }

    @GetMapping("/{id}")
    public BoardResponseDTO getBoardById(@PathVariable int boardId){
        return boardService.getBoardById(boardId);
    }

    @GetMapping("/starred/{userId}/workspace/{workspaceId}")
    public List<BoardResponseDTO> getStarredBoardsByUserAndWorkspace(int userId, int workspaceId) {
        return boardService.getStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @GetMapping("/members/users/{id}")
    public List<BoardResponseDTO> getMemberBoardsByUserId(@PathVariable int userId) {
        return boardService.getMemberBoardsByUserId(userId);
    }

    public List<BoardResponseDTO> getMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        return boardService.getMemberBoardsByUserIdAndWorkspaceId(userId,workspaceId);
    }
}
