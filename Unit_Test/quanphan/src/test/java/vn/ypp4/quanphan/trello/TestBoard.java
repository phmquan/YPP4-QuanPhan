package vn.ypp4.quanphan.trello;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.api.controller.BoardController;
import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TestBoard {
    @Autowired
    private BoardController boardController;

    @Test
    void returnStarredBoardsForUser_Success() {
        //Arrange
        int userId=1;

        //Act
        List<BoardResponseDTO> result = boardController.getStarredBoards(userId);
        //Assert
        assertEquals(1, result.size());
    }

    @Test
    void returnHistoryViewBoardForUser_Success(){
        int userId=2;
        int numBoardRequest=1;
        List<BoardResponseDTO> result=boardController.getHistoryViewedBoards(userId,numBoardRequest);
        assertEquals(1,result.size());
    }

    @Test
    void getBoardById_Success(){
        //Arrange
        int boardId=1;
        //Act
        BoardResponseDTO result=boardController.getBoardById(boardId);
        //Assert
        assertNotNull(result);
    }

    @Test
    void getMemberBoardsByUserId_Success(){
        //Arrange
        int userId=1;
        //Act
        List<BoardResponseDTO> result= boardController.getMemberBoardsByUserId(userId);
        //Assert
        assertEquals(3,result.size());
    }

    @Test
    void getStarredBoardsByUserAndWorkspace_Success(){
        // Arrange
        int userId = 1;
        int workspaceId = 1;
        // Act
        List<BoardResponseDTO> result = boardController.getStarredBoardsByUserAndWorkspace(userId, workspaceId);
        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getMemberBoardsByUserAndWorkspace_Success() {
        // Arrange
        int userId = 1;
        int workspaceId = 1;
        // Act
        List<BoardResponseDTO> result = boardController.getMemberBoardsByUserIdAndWorkspaceId(userId, workspaceId);
        // Assert
        assertEquals(1, result.size());
    }

}
