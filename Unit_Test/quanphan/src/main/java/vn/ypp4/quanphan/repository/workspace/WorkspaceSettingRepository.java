package vn.ypp4.quanphan.repository.workspace;

import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.dto.setting.SettingValueResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceSettingValueResponseDTO;

@Repository
public interface WorkspaceSettingRepository {

    WorkspaceSettingValueResponseDTO findByKeyNameAndOwnerId(String keyName, int workspaceId);
}
