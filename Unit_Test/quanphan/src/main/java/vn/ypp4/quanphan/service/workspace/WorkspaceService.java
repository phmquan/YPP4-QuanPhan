package vn.ypp4.quanphan.service.workspace;

import vn.ypp4.quanphan.domain.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.domain.dto.workspace.WorkspaceUpdateDTO;
import java.util.List;

public interface WorkspaceService {
    List<WorkspaceResponseDTO> getWorkspacesAccesibleByUserId(int userId);
    List<WorkspaceResponseDTO> getMemberWorkspacesByUserId(int userId);
    WorkspaceResponseDTO getWorkspaceById(int workspaceId);
    int updateWorkspace(WorkspaceUpdateDTO updateWorkspace);
}

