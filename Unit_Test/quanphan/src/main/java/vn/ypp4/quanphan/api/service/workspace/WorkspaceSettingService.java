package vn.ypp4.quanphan.api.service.workspace;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceSettingValueResponseDTO;

@Service
public interface WorkspaceSettingService {
    ResponseEntity<WorkspaceSettingValueResponseDTO> getSettingValueByKeyNameAndWorkspaceId(String keyName,
            int WorkspaceId);
}
