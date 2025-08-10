package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.BoardPowerUp;
import vn.ypp4.quanphan.service.mapper.BoardPowerUpRowMapper;

@Service
@RequiredArgsConstructor
public class BoardPowerUpServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final BoardPowerUpRowMapper boardPowerUpRowMapper;
    public void enablePowerUpForBoard(int boardId, int powerUpId) {
        if (isPowerUpEnabled(boardId, powerUpId)) {
            return; // Already enabled
        }
        
        jdbcTemplate.update(
            "INSERT INTO BoardPowerUp (BoardId, PowerUpId, BoardPowerUpStatus) VALUES (?, ?, true) " +
            "ON DUPLICATE KEY UPDATE BoardPowerUpStatus = true",
            boardId, powerUpId);
    }
    public void disablePowerUpForBoard(int boardId, int powerUpId) {
        jdbcTemplate.update(
            "UPDATE BoardPowerUp SET BoardPowerUpStatus = false WHERE BoardId = ? AND PowerUpId = ?",
            boardId, powerUpId);
    }
    public void togglePowerUpStatus(int boardId, int powerUpId) {
        boolean currentStatus = isPowerUpEnabled(boardId, powerUpId);
        
        if (currentStatus) {
            disablePowerUpForBoard(boardId, powerUpId);
        } else {
            enablePowerUpForBoard(boardId, powerUpId);
        }
    }
    public List<BoardPowerUp> getPowerUpsForBoard(int boardId) {
        return jdbcTemplate.query(
            "SELECT * FROM BoardPowerUp WHERE BoardId = ?",
            boardPowerUpRowMapper,
            boardId);
    }
    public List<BoardPowerUp> getBoardsWithPowerUp(int powerUpId) {
        return jdbcTemplate.query(
            "SELECT * FROM BoardPowerUp WHERE PowerUpId = ? AND BoardPowerUpStatus = true",
            boardPowerUpRowMapper,
            powerUpId);
    }
    public boolean isPowerUpEnabled(int boardId, int powerUpId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM BoardPowerUp WHERE BoardId = ? AND PowerUpId = ? AND BoardPowerUpStatus = true",
            Integer.class,
            boardId, powerUpId);
        return count != null && count > 0;
    }
}
