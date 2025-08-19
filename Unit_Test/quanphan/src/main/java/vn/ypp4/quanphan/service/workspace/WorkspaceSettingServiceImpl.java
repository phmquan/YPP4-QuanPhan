package vn.ypp4.quanphan.service.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.dto.workspace.WorkspaceSettingValueResponseDTO;
import vn.ypp4.quanphan.repository.workspace.WorkspaceRepository;
import vn.ypp4.quanphan.repository.workspace.WorkspaceSettingRepository;
import vn.ypp4.quanphan.util.exception.WorkspaceNotFoundException;

@Service
@RequiredArgsConstructor
public class WorkspaceSettingServiceImpl implements WorkspaceSettingService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceSettingRepository settingRepository;
    @Override
    public WorkspaceSettingValueResponseDTO getSettingValueByKeyNameAndWorkspaceId(String keyName, int workspaceId) {
        if(!keyName.isEmpty() && workspaceRepository.existsById(workspaceId)) {
            return settingRepository.findByKeyNameAndOwnerId(keyName, workspaceId);
        }
        else{
            throw new WorkspaceNotFoundException("Workspace or Key Name not found");
        }
    }
}
