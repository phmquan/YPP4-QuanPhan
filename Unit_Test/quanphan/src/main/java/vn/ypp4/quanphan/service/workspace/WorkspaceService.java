package vn.ypp4.quanphan.service.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.WorkspaceResponseDTO;
import vn.ypp4.quanphan.repository.interf.WorkspaceRepository;

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
}
