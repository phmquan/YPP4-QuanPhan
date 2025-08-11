package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.UserStarredBoard;
import vn.ypp4.quanphan.service.mapper.row.UserStarredBoardRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserStarredBoardServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserStarredBoardRowMapper userStarredBoardRowMapper;

    public List<UserStarredBoard> findAll() {
        String sql = "SELECT UserId, BoardId, CreatedAt, StarredBoardsStatus FROM UserStarredBoard";
        return jdbcTemplate.query(sql, userStarredBoardRowMapper);
    }

    public Optional<UserStarredBoard> findByUserIdAndBoardId(int userId, int boardId) {
        String sql = "SELECT UserId, BoardId, CreatedAt, StarredBoardsStatus FROM UserStarredBoard WHERE UserId = ? AND BoardId = ?";
        List<UserStarredBoard> userStarredBoards = jdbcTemplate.query(sql, userStarredBoardRowMapper, userId, boardId);
        return userStarredBoards.isEmpty() ? Optional.empty() : Optional.of(userStarredBoards.get(0));
    }

    public UserStarredBoard save(UserStarredBoard userStarredBoard) {
        Optional<UserStarredBoard> existing = findByUserIdAndBoardId(userStarredBoard.getUserId(),
                userStarredBoard.getBoardId());

        if (existing.isPresent()) {
            return update(userStarredBoard);
        } else {
            return create(userStarredBoard);
        }
    }

    private UserStarredBoard create(UserStarredBoard userStarredBoard) {
        String sql = "INSERT INTO UserStarredBoard (UserId, BoardId, CreatedAt, StarredBoardsStatus) VALUES (?, ?, ?, ?)";

        userStarredBoard.setCreatedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                userStarredBoard.getUserId(),
                userStarredBoard.getBoardId(),
                userStarredBoard.getCreatedAt(),
                userStarredBoard.isStarredBoardsStatus());

        return userStarredBoard;
    }

    private UserStarredBoard update(UserStarredBoard userStarredBoard) {
        String sql = "UPDATE UserStarredBoard SET StarredBoardsStatus = ? WHERE UserId = ? AND BoardId = ?";

        jdbcTemplate.update(sql,
                userStarredBoard.isStarredBoardsStatus(),
                userStarredBoard.getUserId(),
                userStarredBoard.getBoardId());

        return userStarredBoard;
    }

    public void deleteByUserIdAndBoardId(int userId, int boardId) {
        String sql = "DELETE FROM UserStarredBoard WHERE UserId = ? AND BoardId = ?";
        jdbcTemplate.update(sql, userId, boardId);
    }

    public List<UserStarredBoard> findByUserId(int userId) {
        String sql = "SELECT UserId, BoardId, CreatedAt, StarredBoardsStatus FROM UserStarredBoard WHERE UserId = ? AND StarredBoardsStatus = true";
        return jdbcTemplate.query(sql, userStarredBoardRowMapper, userId);
    }

    public List<UserStarredBoard> findByBoardId(int boardId) {
        String sql = "SELECT UserId, BoardId, CreatedAt, StarredBoardsStatus FROM UserStarredBoard WHERE BoardId = ? AND StarredBoardsStatus = true";
        return jdbcTemplate.query(sql, userStarredBoardRowMapper, boardId);
    }

    public void toggleStar(int userId, int boardId) {
        Optional<UserStarredBoard> existing = findByUserIdAndBoardId(userId, boardId);

        if (existing.isPresent()) {
            UserStarredBoard userStarredBoard = existing.get();
            userStarredBoard.setStarredBoardsStatus(!userStarredBoard.isStarredBoardsStatus());
            update(userStarredBoard);
        } else {
            UserStarredBoard newStarredBoard = new UserStarredBoard();
            newStarredBoard.setUserId(userId);
            newStarredBoard.setBoardId(boardId);
            newStarredBoard.setStarredBoardsStatus(true);
            create(newStarredBoard);
        }
    }
}
