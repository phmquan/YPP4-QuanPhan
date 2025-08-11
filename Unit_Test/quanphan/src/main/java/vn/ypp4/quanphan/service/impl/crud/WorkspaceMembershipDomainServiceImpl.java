package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.WorkspaceMembershipDomain;
import vn.ypp4.quanphan.service.mapper.row.WorkspaceMembershipDomainRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceMembershipDomainServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WorkspaceMembershipDomainRowMapper workspaceMembershipDomainRowMapper;

    public List<WorkspaceMembershipDomain> findAll() {
        String sql = "SELECT Id, WorkspaceId, Domain, CreatedAt FROM WorkspaceMembershipDomain";
        return jdbcTemplate.query(sql, workspaceMembershipDomainRowMapper);
    }

    public Optional<WorkspaceMembershipDomain> findById(int id) {
        String sql = "SELECT Id, WorkspaceId, Domain, CreatedAt FROM WorkspaceMembershipDomain WHERE Id = ?";
        List<WorkspaceMembershipDomain> workspaceMembershipDomains = jdbcTemplate.query(sql,
                workspaceMembershipDomainRowMapper, id);
        return workspaceMembershipDomains.isEmpty() ? Optional.empty() : Optional.of(workspaceMembershipDomains.get(0));
    }

    public WorkspaceMembershipDomain save(WorkspaceMembershipDomain workspaceMembershipDomain) {
        if (workspaceMembershipDomain.getId() == 0) {
            return create(workspaceMembershipDomain);
        } else {
            return update(workspaceMembershipDomain);
        }
    }

    private WorkspaceMembershipDomain create(WorkspaceMembershipDomain workspaceMembershipDomain) {
        String sql = "INSERT INTO WorkspaceMembershipDomain (WorkspaceId, Domain, CreatedAt) VALUES (?, ?, ?)";

        workspaceMembershipDomain.setCreatedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                workspaceMembershipDomain.getWorkspaceId(),
                workspaceMembershipDomain.getDomain(),
                workspaceMembershipDomain.getCreatedAt());

        return workspaceMembershipDomain;
    }

    private WorkspaceMembershipDomain update(WorkspaceMembershipDomain workspaceMembershipDomain) {
        String sql = "UPDATE WorkspaceMembershipDomain SET WorkspaceId = ?, Domain = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                workspaceMembershipDomain.getWorkspaceId(),
                workspaceMembershipDomain.getDomain(),
                workspaceMembershipDomain.getId());

        return workspaceMembershipDomain;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM WorkspaceMembershipDomain WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<WorkspaceMembershipDomain> findByWorkspaceId(int workspaceId) {
        String sql = "SELECT Id, WorkspaceId, Domain, CreatedAt FROM WorkspaceMembershipDomain WHERE WorkspaceId = ?";
        return jdbcTemplate.query(sql, workspaceMembershipDomainRowMapper, workspaceId);
    }

    public Optional<WorkspaceMembershipDomain> findByWorkspaceIdAndDomain(int workspaceId, String domain) {
        String sql = "SELECT Id, WorkspaceId, Domain, CreatedAt FROM WorkspaceMembershipDomain WHERE WorkspaceId = ? AND Domain = ?";
        List<WorkspaceMembershipDomain> workspaceMembershipDomains = jdbcTemplate.query(sql,
                workspaceMembershipDomainRowMapper, workspaceId, domain);
        return workspaceMembershipDomains.isEmpty() ? Optional.empty() : Optional.of(workspaceMembershipDomains.get(0));
    }

    public List<WorkspaceMembershipDomain> findByDomain(String domain) {
        String sql = "SELECT Id, WorkspaceId, Domain, CreatedAt FROM WorkspaceMembershipDomain WHERE Domain = ?";
        return jdbcTemplate.query(sql, workspaceMembershipDomainRowMapper, domain);
    }

    public boolean isDomainAllowedForWorkspace(int workspaceId, String domain) {
        return findByWorkspaceIdAndDomain(workspaceId, domain).isPresent();
    }
}
