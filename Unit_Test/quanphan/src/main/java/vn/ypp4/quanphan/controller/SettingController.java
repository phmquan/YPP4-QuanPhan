package vn.ypp4.quanphan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.ypp4.quanphan.dto.setting.SettingValueResponseDTO;
import vn.ypp4.quanphan.service.workspace.WorkspaceSettingService;

@Controller
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class SettingController {

}
