package vn.ypp4.quanphan.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.ypp4.quanphan.api.controller.BoardController;
import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.api.service.board.BoardService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestBoard {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    private BoardResponseDTO sampleBoard;
    private List<BoardResponseDTO> sampleBoards;

    @BeforeEach
    void setUp() {
        sampleBoard = new BoardResponseDTO();
        sampleBoards = List.of(sampleBoard);
    }

    @Test
    void getStarredBoards_Success() {
        // Arrange
        int userId = 1;
        ResponseEntity<List<BoardResponseDTO>> expectedResponse = ResponseEntity.ok(sampleBoards);
        when(boardService.getStarredBoardsByUserId(userId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<List<BoardResponseDTO>> result = boardController.getStarredBoards(userId);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(boardService, times(1)).getStarredBoardsByUserId(userId);
    }

    @Test
    void getHistoryViewedBoards_Success() {
        // Arrange
        int userId = 2;
        int numBoardRequest = 1;
        ResponseEntity<List<BoardResponseDTO>> expectedResponse = ResponseEntity.ok(sampleBoards);
        when(boardService.getViewedBoardsByUserId(userId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<List<BoardResponseDTO>> result = boardController.getHistoryViewedBoards(userId, numBoardRequest);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(boardService, times(1)).getViewedBoardsByUserId(userId);
    }

    @Test
    void getBoardById_Success() {
        // Arrange
        int boardId = 1;
        ResponseEntity<BoardResponseDTO> expectedResponse = ResponseEntity.ok(sampleBoard);
        when(boardService.getBoardById(boardId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<BoardResponseDTO> result = boardController.getBoardById(boardId);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(boardService, times(1)).getBoardById(boardId);
    }

    @Test
    void getBoardById_NotFound() {
        // Arrange
        int boardId = 999;
        ResponseEntity<BoardResponseDTO> expectedResponse = ResponseEntity.notFound().build();
        when(boardService.getBoardById(boardId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<BoardResponseDTO> result = boardController.getBoardById(boardId);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(boardService, times(1)).getBoardById(boardId);
    }

    @Test
    void getMemberBoardsByUserId_Success() {
        // Arrange
        int userId = 1;
        List<BoardResponseDTO> multipleBoards = Arrays.asList(sampleBoard, sampleBoard, sampleBoard);
        ResponseEntity<List<BoardResponseDTO>> expectedResponse = ResponseEntity.ok(multipleBoards);
        when(boardService.getMemberBoardsByUserId(userId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<List<BoardResponseDTO>> result = boardController.getMemberBoardsByUserId(userId);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(3, result.getBody().size());
        verify(boardService, times(1)).getMemberBoardsByUserId(userId);
    }

    @Test
    void getStarredBoardsByUserAndWorkspace_Success() {
        // Arrange
        int userId = 1;
        int workspaceId = 1;
        ResponseEntity<List<BoardResponseDTO>> expectedResponse = ResponseEntity.ok(sampleBoards);
        when(boardService.getStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<List<BoardResponseDTO>> result = boardController.getStarredBoardsByUserAndWorkspace(userId,
                workspaceId);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(boardService, times(1)).getStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @Test
    void getMemberBoardsByUserAndWorkspace_Success() {
        // Arrange
        int userId = 1;
        int workspaceId = 1;
        ResponseEntity<List<BoardResponseDTO>> expectedResponse = ResponseEntity.ok(sampleBoards);
        when(boardService.getMemberBoardsByUserIdAndWorkspaceId(userId, workspaceId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<List<BoardResponseDTO>> result = boardController.getMemberBoardsByUserIdAndWorkspaceId(userId,
                workspaceId);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(boardService, times(1)).getMemberBoardsByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @Test
    void getStarredBoards_InternalServerError() {
        // Arrange
        int userId = 1;
        ResponseEntity<List<BoardResponseDTO>> expectedResponse = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        when(boardService.getStarredBoardsByUserId(userId)).thenReturn(expectedResponse);
        // Act
        ResponseEntity<List<BoardResponseDTO>> result = boardController.getStarredBoards(userId);
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(boardService, times(1)).getStarredBoardsByUserId(userId);
    }
}
