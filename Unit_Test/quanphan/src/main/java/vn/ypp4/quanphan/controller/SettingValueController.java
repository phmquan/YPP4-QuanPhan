package vn.ypp4.quanphan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ypp4.quanphan.domain.dto.setting.SettingValueResponseDTO;
import vn.ypp4.quanphan.service.setting.SettingValueService;

@RestController
@RequestMapping("/api/v1/settings/values")
@RequiredArgsConstructor
public class SettingValueController {
    private final SettingValueService settingValueService;
    @GetMapping("/{keyname}/{id}")
    public SettingValueResponseDTO getSettingValueForWorkspaceByKeyNameAndId(@PathVariable String keyName, @PathVariable int workspaceId) {
        return settingValueService.getSettingValueForWorkspaceByKeyNameAndId(keyName, workspaceId);
    }
}
