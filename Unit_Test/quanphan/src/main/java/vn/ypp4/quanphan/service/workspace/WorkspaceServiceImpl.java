package vn.ypp4.quanphan.service.workspace;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceUpdateDTO;

import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Override
    public List<WorkspaceResponseDTO> getMemberWorkspacesByUserId(int userId) {
        return List.of();
    }

    @Override
    public WorkspaceResponseDTO getWorkspaceById(int workspaceId) {
        return null;
    }

    @Override
    public int updateWorkspace(WorkspaceUpdateDTO updateWorkspace) {
        return 0;
    }
}
