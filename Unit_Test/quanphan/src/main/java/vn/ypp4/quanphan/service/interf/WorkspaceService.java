package vn.ypp4.quanphan.service.interf;

import java.time.Instant;
import java.util.List;

import vn.ypp4.quanphan.domain.Workspace;

public interface WorkspaceService {
    Workspace createWorkspace(String name, String description, int CategoryId, Instant createdAt, int createdBy,
            Instant updatedAt, int updatedBy, String logoUrl);

    Workspace getWorkspaceById(int id);

    Workspace getWorkspaceByName(String name);

    List<Workspace> getAllWorkspaces();

    int updateWorkspaceById(int id, String name, String description, int categoryId, Instant updatedAt, int updatedBy,
            String logoUrl);

    int deleteWorkspace(int id);
}
