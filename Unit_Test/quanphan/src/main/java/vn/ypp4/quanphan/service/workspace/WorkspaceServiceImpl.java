package vn.ypp4.quanphan.service.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceUpdateDTO;
import vn.ypp4.quanphan.repository.workspace.WorkspaceRepository;
import vn.ypp4.quanphan.util.exception.WorkspaceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Override
    public List<WorkspaceResponseDTO> getMemberWorkspacesByUserId(int userId) {
        return workspaceRepository.findMemberWorkspacesByUserId(userId);
    }

    @Override
    public WorkspaceResponseDTO getWorkspaceById(int workspaceId) {
        return workspaceRepository.findById(workspaceId);
    }

    @Override
    public int updateWorkspace(WorkspaceUpdateDTO updateWorkspace) {
        if (workspaceRepository.existsById(updateWorkspace.getId())) {
            updateWorkspace.setUpdatedAt(LocalDateTime.now());
            updateWorkspace.setUpdatedBy(updateWorkspace.getUpdatedBy());
            return workspaceRepository.update(updateWorkspace);
        } else {
            throw new WorkspaceNotFoundException("Workspace not found");
        }
    }
}
