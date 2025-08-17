package vn.ypp4.quanphan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.service.board.BoardService;
import vn.ypp4.quanphan.service.board.StarredBoardServiceImpl;

import vn.ypp4.quanphan.service.board.ViewHistoryServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/api/v1/boards")

public class BoardController {
    @Autowired
    private  StarredBoardServiceImpl starredBoardService;
    @Autowired
    private  ViewHistoryServiceImpl viewHistoryService;
    @Autowired
    private  BoardService boardService;

    @GetMapping("/starred/{id}")
    public List<BoardResponseDTO> getStarredBoards(@RequestParam int userId) {
        return starredBoardService.getStarredBoardsByUserId(userId);
    }
    @GetMapping("/viewed/{id}")
    public List<BoardResponseDTO> getHistoryViewedBoards(@RequestParam int userId,@RequestParam int requestNumBoard){
        return viewHistoryService.getRecentlyViewedBoardsByUserId(userId,requestNumBoard);
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
    public List<BoardResponseDTO> getStarredBoardsByUserAndWorkspace(int userId, int workspaceId) {
        return starredBoardService.getStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @GetMapping("/member/{userId}/workspace/{workspaceId}")
    public List<BoardResponseDTO> getMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        return boardService.getMemberBoardByUserIdAndWorkspaceId(userId, workspaceId);
    }
}
