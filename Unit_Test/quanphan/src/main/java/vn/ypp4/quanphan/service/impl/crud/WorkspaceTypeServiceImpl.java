package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.WorkspaceType;
import vn.ypp4.quanphan.service.mapper.row.WorkspaceTypeRowMapper;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceTypeServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WorkspaceTypeRowMapper workspaceTypeRowMapper;

    public List<WorkspaceType> findAll() {
        String sql = "SELECT Id, TypeValue, DisplayValue FROM WorkspaceType";
        return jdbcTemplate.query(sql, workspaceTypeRowMapper);
    }

    public Optional<WorkspaceType> findById(int id) {
        String sql = "SELECT Id, TypeValue, DisplayValue FROM WorkspaceType WHERE Id = ?";
        List<WorkspaceType> workspaceTypes = jdbcTemplate.query(sql, workspaceTypeRowMapper, id);
        return workspaceTypes.isEmpty() ? Optional.empty() : Optional.of(workspaceTypes.get(0));
    }

    public WorkspaceType save(WorkspaceType workspaceType) {
        if (workspaceType.getId() == 0) {
            return create(workspaceType);
        } else {
            return update(workspaceType);
        }
    }

    private WorkspaceType create(WorkspaceType workspaceType) {
        String sql = "INSERT INTO WorkspaceType (TypeValue, DisplayValue) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                workspaceType.getTypeValue(),
                workspaceType.getDisplayValue());

        return workspaceType;
    }

    private WorkspaceType update(WorkspaceType workspaceType) {
        String sql = "UPDATE WorkspaceType SET TypeValue = ?, DisplayValue = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                workspaceType.getTypeValue(),
                workspaceType.getDisplayValue(),
                workspaceType.getId());

        return workspaceType;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM WorkspaceType WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<WorkspaceType> findByTypeValue(String typeValue) {
        String sql = "SELECT Id, TypeValue, DisplayValue FROM WorkspaceType WHERE TypeValue = ?";
        List<WorkspaceType> workspaceTypes = jdbcTemplate.query(sql, workspaceTypeRowMapper, typeValue);
        return workspaceTypes.isEmpty() ? Optional.empty() : Optional.of(workspaceTypes.get(0));
    }
}
