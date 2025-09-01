package vn.ypp4.quanphan.api.service.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceUpdateDTO;
import vn.ypp4.quanphan.api.repository.workspace.WorkspaceRepository;
import vn.ypp4.quanphan.api.util.exception.WorkspaceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Override
    public ResponseEntity<List<WorkspaceResponseDTO>> getMemberWorkspacesByUserId(int userId) {
        try {
            List<WorkspaceResponseDTO> workspaces = workspaceRepository.findMemberWorkspacesByUserId(userId);
            return ResponseEntity.ok(workspaces);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<WorkspaceResponseDTO> getWorkspaceById(int workspaceId) {
        try {
            WorkspaceResponseDTO workspace = workspaceRepository.findById(workspaceId);
            return workspace!=null?ResponseEntity.ok(workspace):
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Integer> updateWorkspace(WorkspaceUpdateDTO updateWorkspace) {
        try {
            if (workspaceRepository.existsById(updateWorkspace.getId())) {
                updateWorkspace.setUpdatedAt(LocalDateTime.now());
                updateWorkspace.setUpdatedBy(updateWorkspace.getUpdatedBy());
                int result = workspaceRepository.update(updateWorkspace);
                return ResponseEntity.ok(result);
            } else {
                throw new WorkspaceNotFoundException("Workspace not found");
            }
        } catch (WorkspaceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
