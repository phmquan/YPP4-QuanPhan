package vn.ypp4.quanphan.service;

import java.time.Instant;

//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.util.constant.WorkspaceTypeEnum;

@Service
@RequiredArgsConstructor

public class WorkspaceService {
    // private final JdbcTemplate jdbcTemplate;

    public Workspace createWorkspace(String name, String Description, WorkspaceTypeEnum Type, Instant CreatedAt,
            int CreatedBy) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null or empty for workspace");
        }
        if (Type == null) {
            throw new IllegalArgumentException("Type cannot be null or empty for workspace");
        }
        return new Workspace(CreatedBy, name, Description, Type, CreatedAt, CreatedBy);
    }
}
