package vn.ypp4.quanphan.service.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.domain.dto.workspace.WorkspaceUpdateDTO;
import vn.ypp4.quanphan.repository.WorkspaceRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    List<WorkspaceResponseDTO> getWorkspacesAccesibleByUserId(int userId){
        return workspaceRepository.findAccessibleWorkspacesByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(WorkspaceResponseDTO::new)
                .toList();
    }

    public List<WorkspaceResponseDTO> getMemberWorkspacesByUserId(int userId) {
        return workspaceRepository.findMemberWorkspacesByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(WorkspaceResponseDTO::new)
                .toList();


    }

    public WorkspaceResponseDTO getWorkspaceById(int workspaceId) {
        return new WorkspaceResponseDTO(workspaceRepository.findById(workspaceId));
    }

    public int updateWorkspace(WorkspaceUpdateDTO updateWorkspace) {
        return workspaceRepository.updateWorkspace(updateWorkspace);
    }
}
