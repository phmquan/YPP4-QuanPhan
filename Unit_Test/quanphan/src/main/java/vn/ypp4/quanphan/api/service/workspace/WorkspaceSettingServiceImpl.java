package vn.ypp4.quanphan.api.service.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.api.dto.workspace.WorkspaceSettingValueResponseDTO;
import vn.ypp4.quanphan.api.repository.workspace.WorkspaceRepository;
import vn.ypp4.quanphan.api.repository.workspace.WorkspaceSettingRepository;
import vn.ypp4.quanphan.api.util.exception.WorkspaceNotFoundException;

@Service
@RequiredArgsConstructor
public class WorkspaceSettingServiceImpl implements WorkspaceSettingService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceSettingRepository settingRepository;

    @Override
    public ResponseEntity<WorkspaceSettingValueResponseDTO> getSettingValueByKeyNameAndWorkspaceId(String keyName,
            int workspaceId) {
        try {
            if (!keyName.isEmpty() && workspaceRepository.existsById(workspaceId)) {
                WorkspaceSettingValueResponseDTO setting = settingRepository.findByKeyNameAndOwnerId(keyName,
                        workspaceId);
                if (setting != null) {
                    return ResponseEntity.ok(setting);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                throw new WorkspaceNotFoundException("Workspace or Key Name not found");
            }
        } catch (WorkspaceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
