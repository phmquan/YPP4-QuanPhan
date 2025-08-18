package vn.ypp4.quanphan.service.workspace;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceUpdateDTO;

import java.util.List;

@Service
public interface WorkspaceService {
    List<WorkspaceResponseDTO> getMemberWorkspacesByUserId(int userId);

    WorkspaceResponseDTO getWorkspaceById(int workspaceId);

    int updateWorkspace(WorkspaceUpdateDTO updateWorkspace);
}
