package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Collections;
import vn.ypp4.quanphan.service.mapper.row.CollectionsRowMapper;

@Service
@RequiredArgsConstructor
public class CollectionsServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CollectionsRowMapper collectionsRowMapper;

    @Transactional
    public Collections createCollection(String collectionName, LocalDateTime createdAt, int createdBy,
            int workspaceId) {
        if (collectionName == null || collectionName.isBlank()) {
            throw new IllegalArgumentException("Collection name cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        // Check if collection name already exists in this workspace
        if (isCollectionNameExists(collectionName, workspaceId)) {
            throw new IllegalStateException(
                    "A collection with name '" + collectionName + "' already exists in this workspace");
        }

        // Set updatedAt to createdAt for new records
        LocalDateTime updatedAt = createdAt;
        int updatedBy = createdBy;

        jdbcTemplate.update(
                "INSERT INTO Collections (CollectionName, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, WorkspaceId) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                collectionName, createdAt, createdBy, updatedAt, updatedBy, workspaceId);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Collections WHERE Id = LAST_INSERT_ID()",
                collectionsRowMapper);
    }

    public Collections getCollectionById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Collections WHERE Id = ?",
                    collectionsRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Collections> getCollectionsByWorkspace(int workspaceId) {
        return jdbcTemplate.query(
                "SELECT * FROM Collections WHERE WorkspaceId = ? ORDER BY CollectionName",
                collectionsRowMapper,
                workspaceId);
    }

    public List<Collections> getAllCollections() {
        return jdbcTemplate.query(
                "SELECT * FROM Collections ORDER BY WorkspaceId, CollectionName",
                collectionsRowMapper);
    }

    @Transactional
    public int updateCollection(int id, String collectionName, LocalDateTime updatedAt, int updatedBy) {
        if (collectionName != null && collectionName.isBlank()) {
            throw new IllegalArgumentException("Collection name cannot be empty");
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }

        // Get existing collection to merge with updates
        Collections existingCollection = getCollectionById(id);
        if (existingCollection == null) {
            throw new IllegalArgumentException("Collection with ID " + id + " not found");
        }

        String finalCollectionName = collectionName != null ? collectionName : existingCollection.getCollectionName();

        // If collection name is being changed, check for duplicates in the same
        // workspace
        if (collectionName != null && !finalCollectionName.equalsIgnoreCase(existingCollection.getCollectionName())) {
            if (isCollectionNameExists(finalCollectionName, existingCollection.getWorkspaceId())) {
                throw new IllegalStateException(
                        "Another collection with name '" + finalCollectionName + "' already exists in this workspace");
            }
        }

        return jdbcTemplate.update(
                "UPDATE Collections SET CollectionName = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?",
                finalCollectionName, updatedAt, updatedBy, id);
    }

    @Transactional
    public int deleteCollection(int id) {
        // First check if the collection is being used by any boards
        Integer usageCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Board WHERE CollectionId = ?",
                Integer.class,
                id);

        if (usageCount != null && usageCount > 0) {
            throw new IllegalStateException("Cannot delete collection as it contains " + usageCount + " board(s)");
        }

        return jdbcTemplate.update(
                "DELETE FROM Collections WHERE Id = ?",
                id);
    }

    public boolean isCollectionNameExists(String collectionName, int workspaceId) {
        if (collectionName == null || collectionName.isBlank()) {
            throw new IllegalArgumentException("Collection name cannot be null or empty");
        }

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Collections WHERE LOWER(CollectionName) = LOWER(?) AND WorkspaceId = ?",
                Integer.class,
                collectionName, workspaceId);

        return count != null && count > 0;
    }
}
