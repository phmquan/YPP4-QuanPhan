package vn.ypp4.quanphan.service.setting;

import vn.ypp4.quanphan.domain.dto.setting.SettingValueResponseDTO;

public interface SettingValueService {
    public SettingValueResponseDTO getSettingValueForWorkspaceByKeyNameAndId(String keyName, int workspaceId);
}
