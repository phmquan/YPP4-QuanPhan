package vn.ypp4.quanphan.service.workspace;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.setting.SettingValueResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceSettingValueResponseDTO;

@Service
public interface WorkspaceSettingService {
    WorkspaceSettingValueResponseDTO getSettingValueByKeyNameAndWorkspaceId(String keyName, int WorkspaceId);
}
