import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.domain.User;
import vn.ypp4.quanphan.domain.UserFavoritedBoard;
import vn.ypp4.quanphan.repository.interf.BoardRepository;
import vn.ypp4.quanphan.repository.interf.UserFavoritedBoardRepository;
import vn.ypp4.quanphan.repository.interf.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Để Spring Boot load application-test.properties
class UserFavoritedBoardRepositoryTest {

    @Autowired
    private UserFavoritedBoardRepository starredBoardRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Board testBoard;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFullName("Test User");
        userRepository.createUser(testUser);

        testBoard = new Board();
        testBoard.setBoardName("Test Board");
        testBoard.setBoardDescription("Test Description");
        testBoard.setBackgroundUrl("test-background.jpg");
        boardRepository.createBoard(testBoard);

        UserFavoritedBoard starred = new UserFavoritedBoard();
        starred.setUserId(testUser.getId());
        starred.setBoardId(testBoard.getId());
        starred.setCreatedAt(LocalDateTime.now());
        starred.setStarredBoardsStatus(true);
        starredBoardRepository.createFavoritedBoardUser(starred);
    }

    @Test
    void shouldReturnStarredBoardsForUser() {
        List<UserFavoritedBoard> result = starredBoardRepository.getFavoritedBoardsByUserId(testUser.getId());
        assertEquals(1, result.size());
        assertEquals(testBoard.getId(), result.get(0).getBoardId());
    }
}
