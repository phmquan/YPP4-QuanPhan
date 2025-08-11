package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.Stage;
import vn.ypp4.quanphan.service.mapper.StageRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StageServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StageRowMapper stageRowMapper;

    public List<Stage> findAll() {
        String sql = "SELECT Id, Title, CreatedAt, CreatedBy, BoardId, StageStatus, ColorId, Position, UpdatedAt, UpdatedBy FROM Stage";
        return jdbcTemplate.query(sql, stageRowMapper);
    }

    public Optional<Stage> findById(int id) {
        String sql = "SELECT Id, Title, CreatedAt, CreatedBy, BoardId, StageStatus, ColorId, Position, UpdatedAt, UpdatedBy FROM Stage WHERE Id = ?";
        List<Stage> stages = jdbcTemplate.query(sql, stageRowMapper, id);
        return stages.isEmpty() ? Optional.empty() : Optional.of(stages.get(0));
    }

    public Stage save(Stage stage) {
        if (stage.getId() == 0) {
            return create(stage);
        } else {
            return update(stage);
        }
    }

    private Stage create(Stage stage) {
        String sql = "INSERT INTO Stage (Title, CreatedAt, CreatedBy, BoardId, StageStatus, ColorId, Position, UpdatedAt, UpdatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();
        stage.setCreatedAt(now);
        stage.setUpdatedAt(now);

        jdbcTemplate.update(sql,
                stage.getTitle(),
                stage.getCreatedAt(),
                stage.getCreatedBy(),
                stage.getBoardId(),
                stage.getStageStatus(),
                stage.getColorId(),
                stage.getPosition(),
                stage.getUpdatedAt(),
                stage.getUpdatedBy());

        return stage;
    }

    private Stage update(Stage stage) {
        String sql = "UPDATE Stage SET Title = ?, StageStatus = ?, ColorId = ?, Position = ?, UpdatedAt = ?, UpdatedBy = ? WHERE Id = ?";

        stage.setUpdatedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                stage.getTitle(),
                stage.getStageStatus(),
                stage.getColorId(),
                stage.getPosition(),
                stage.getUpdatedAt(),
                stage.getUpdatedBy(),
                stage.getId());

        return stage;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Stage WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Stage> findByBoardId(int boardId) {
        String sql = "SELECT Id, Title, CreatedAt, CreatedBy, BoardId, StageStatus, ColorId, Position, UpdatedAt, UpdatedBy FROM Stage WHERE BoardId = ? ORDER BY Position";
        return jdbcTemplate.query(sql, stageRowMapper, boardId);
    }
}
