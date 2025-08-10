package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.BoardCollection;
import vn.ypp4.quanphan.service.mapper.BoardCollectionRowMapper;

@Service
@RequiredArgsConstructor
public class BoardCollectionServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final BoardCollectionRowMapper boardCollectionRowMapper;
    public void addBoardToCollection(int boardId, int collectionId) {
        if (isBoardInCollection(boardId, collectionId)) {
            throw new IllegalStateException("Board is already in the collection");
        }
        
        jdbcTemplate.update(
            "INSERT INTO BoardCollection (BoardId, CollectionId) VALUES (?, ?)",
            boardId, collectionId);
    }
    public void removeBoardFromCollection(int boardId, int collectionId) {
        jdbcTemplate.update(
            "DELETE FROM BoardCollection WHERE BoardId = ? AND CollectionId = ?",
            boardId, collectionId);
    }
    public List<BoardCollection> getCollectionsForBoard(int boardId) {
        return jdbcTemplate.query(
            "SELECT * FROM BoardCollection WHERE BoardId = ?",
            boardCollectionRowMapper,
            boardId);
    }
    public List<BoardCollection> getBoardsInCollection(int collectionId) {
        return jdbcTemplate.query(
            "SELECT * FROM BoardCollection WHERE CollectionId = ?",
            boardCollectionRowMapper,
            collectionId);
    }
    public boolean isBoardInCollection(int boardId, int collectionId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM BoardCollection WHERE BoardId = ? AND CollectionId = ?",
            Integer.class,
            boardId, collectionId);
        return count != null && count > 0;
    }
}
