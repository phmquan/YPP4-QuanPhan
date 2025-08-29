package vn.ypp4.quanphan.api.repository.workspace;

import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceSettingValueResponseDTO;

@Repository
public interface WorkspaceSettingRepository {

    WorkspaceSettingValueResponseDTO findByKeyNameAndOwnerId(String keyName, int workspaceId);
}
