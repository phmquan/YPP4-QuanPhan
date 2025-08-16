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
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    @Override
    public List<WorkspaceResponseDTO> getWorkspacesAccesibleByUserId(int userId){
        return workspaceRepository.findWorkspacesAccessibleByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(WorkspaceResponseDTO::new)
                .toList();
    }
    @Override
    public List<WorkspaceResponseDTO> getMemberWorkspacesByUserId(int userId) {
        return workspaceRepository.findWorkspacesByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(WorkspaceResponseDTO::new)
                .toList();
    }
    @Override
    public WorkspaceResponseDTO getWorkspaceById(int workspaceId) {
        return new WorkspaceResponseDTO(workspaceRepository.findWorkspaceById(workspaceId));
    }
    @Override
    public int updateWorkspace(WorkspaceUpdateDTO updateWorkspace) {
        return workspaceRepository.updateWorkspace(updateWorkspace);
    }
}
