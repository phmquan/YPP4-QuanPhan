package vn.ypp4.quanphan.repository.workspace;

import vn.ypp4.quanphan.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceUpdateDTO;

import java.util.List;

public interface WorkspaceRepository {
    boolean existsById(int id);

    int update(WorkspaceUpdateDTO updateWorkspace);

    WorkspaceResponseDTO findById(int workspaceId);

    List<WorkspaceResponseDTO> findMemberWorkspacesByUserId(int userId);
}
