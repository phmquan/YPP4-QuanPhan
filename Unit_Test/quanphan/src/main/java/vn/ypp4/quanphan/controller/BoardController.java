package vn.ypp4.quanphan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.service.board.BoardService;
import vn.ypp4.quanphan.service.board.UserStarredBoardService;
import vn.ypp4.quanphan.service.board.UserViewHistoryService;

import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {
    private final UserStarredBoardService userStarredBoardService;
    private final UserViewHistoryService userViewHistoryService;
    private final BoardService boardService;

    @GetMapping("/starred/{id}")
    public Iterator<BoardResponseDTO> getStarredBoards(@RequestParam int userId) {
        return userStarredBoardService.getStarredBoardsByUserId(userId);
    }
    @GetMapping("/viewed/{id}")
    public Iterator<BoardResponseDTO> getHistoryViewedBoards(@RequestParam int userId,@RequestParam int requestNumBoard){
        return userViewHistoryService.getRecentlyViewedBoardsByUserId(userId,requestNumBoard);
    }

    public int createBoard(BoardCreateDTO createBoard) {
        return boardService.createBoard(createBoard);
    }
    @GetMapping("/{id}")
    public BoardResponseDTO getBoardById(@PathVariable int boardId){
        return boardService.getBoardById(boardId);
    }
    @GetMapping("/member/{id}")
    public List<BoardResponseDTO> getMemberBoardsByUserId(@PathVariable int userId) {
        return boardService.getMemberBoardByUserId(userId);
    }

    @GetMapping("/starred/{userId}/workspace/{workspaceId}")
    public Iterator<BoardResponseDTO> getStarredBoardsByUserAndWorkspace(int userId, int workspaceId) {
        return userStarredBoardService.getStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @GetMapping("/member/{userId}/workspace/{workspaceId}")
    public List<BoardResponseDTO> getMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        return boardService.getMemberBoardByUserIdAndWorkspaceId(userId, workspaceId);
    }
}
