package vn.ypp4.quanphan.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.domain.User;

import vn.ypp4.quanphan.repository.BoardRepository;
import vn.ypp4.quanphan.repository.UserStarredBoardRepository;
import vn.ypp4.quanphan.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // Để Spring Boot load application-test.properties
class UserStarredBoardServiceTest {

    @Autowired
    private UserStarredBoardRepository starredBoardRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Board testBoard;

   

    @Test
    void shouldReturnStarredBoardsForUser() {
        List<Board> result = starredBoardRepository.getStarredBoardsByUserId(1);
        assertEquals(1, result.size());
        assertEquals("Project Alpha", result.getFirst().getBoardName());
    }
}
