package vn.ypp4.quanphan.api.service.workspace;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceUpdateDTO;

import java.util.List;

@Service
public interface WorkspaceService {
    ResponseEntity<List<WorkspaceResponseDTO>> getMemberWorkspacesByUserId(int userId);

    ResponseEntity<WorkspaceResponseDTO> getWorkspaceById(int workspaceId);

    ResponseEntity<Integer> updateWorkspace(WorkspaceUpdateDTO updateWorkspace);

}
