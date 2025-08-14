package vn.ypp4.quanphan.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.BoardController;

import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // Để Spring Boot load application-test.properties
class TestBoard {
    @Autowired
    private BoardController boardController;
    @Test
    void returnStarredBoardsForUser_Success() {
        //Arrange
        int userId=1;
        List<BoardResponseDTO> expected=new ArrayList<>();
        expected.add(new BoardResponseDTO(1,"Project Alpha","bg1.jpg"));
        //Act
        Iterator<BoardResponseDTO> result = boardController.getStarredBoards(userId);
        //Assert
        assertEquals(expected.getFirst().getBoardName(), result.next().getBoardName());
    }
    @Test
    void returnHistoryViewBoardForUser_Success(){
        int userId=2;
        int numBoardRequest=1;
        List<BoardResponseDTO> expected=new ArrayList<>();
        expected.add(new BoardResponseDTO(2,"Project Beta","Second Project"));
        Iterator<BoardResponseDTO> result=boardController.getHistoryViewedBoards(userId,numBoardRequest);
        assertEquals(expected.getFirst().getBoardName(),result.next().getBoardName());
    }
    @Test
    void createBoard_Success(){
        //Arrange
        BoardCreateDTO createBoard=new BoardCreateDTO();
        createBoard.setBoardName("Board Insert");
        createBoard.setBackgroundUrl("background1.jpg");
        createBoard.setWorkspaceId(1);
        //Act
        int result=boardController.createBoard(createBoard);
        //Assert
        assertEquals(1,result);
    }
    @Test
    void getBoardById_Success(){
        //Arrange
        int boardId=1;
        BoardResponseDTO expected= new BoardResponseDTO();
        expected.setBoardId(1);
        expected.setBoardName("Project Alpha");
        expected.setBackgroundUrl("bg1.jpg");

        //Act
        BoardResponseDTO result=boardController.getBoardById(boardId);

        //Assert
        assertEquals(expected.getBoardName(),result.getBoardName());
        assertEquals(expected.getBackgroundUrl(),result.getBackgroundUrl());
    }
    @Test
    void getMemberBoardsByUserId_Success(){
        //Arrange
        int userId=1;
        List<BoardResponseDTO> expected=new ArrayList<>();
        expected.add(new BoardResponseDTO(1, "Project Alpha", "bg1.jpg"));
        expected.add(new BoardResponseDTO(2, "Project Beta", "bg2.jpg"));
        expected.add(new BoardResponseDTO(3, "Project Gamma", "bg3.jpg"));

        //Act
        List<BoardResponseDTO> result= boardController.getMemberBoardsByUserId(userId);

        //Assert
        assertEquals(expected.size(),result.size());
    }

    @Test
    void getStarredBoardsByUserAndWorkspace_Success(){
        // Arrange
        int userId = 1;
        int workspaceId = 1;
        List<BoardResponseDTO> expected = new ArrayList<>();
        expected.add(new BoardResponseDTO(1, "Project Alpha", "bg1.jpg"));

        // Act
        Iterator<BoardResponseDTO> result = boardController.getStarredBoardsByUserAndWorkspace(userId, workspaceId);

        // Assert
        assertTrue(result.hasNext());
        assertEquals(expected.getFirst().getBoardName(), result.next().getBoardName());
    }
    @Test
    void getMemberBoardsByUserAndWorkspace_Success() {
        // Arrange
        int userId = 1;
        int workspaceId = 1;
        List<BoardResponseDTO> expected = new ArrayList<>();
        expected.add(new BoardResponseDTO(1, "Project Alpha", "bg1.jpg"));

        // Act
        List<BoardResponseDTO> result = boardController.getMemberBoardsByUserIdAndWorkspaceId(userId, workspaceId);

        // Assert
        assertEquals(expected.size(), result.size());
    }
}
