package vn.ypp4.quanphan.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ypp4.quanphan.api.dto.member.MemberWorkspaceResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceSettingValueResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceUpdateDTO;
import vn.ypp4.quanphan.api.service.workspace.WorkspaceMemberService;
import vn.ypp4.quanphan.api.service.workspace.WorkspaceService;
import vn.ypp4.quanphan.api.service.workspace.WorkspaceSettingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final WorkspaceSettingService workspaceSettingService;
    private final WorkspaceMemberService workspaceMemberService;

    @GetMapping("/member")
    public ResponseEntity<List<WorkspaceResponseDTO>> getWorkspaceUserIsMember(@RequestParam int userId) {
        return workspaceService.getMemberWorkspacesByUserId(userId);
    }

    @GetMapping
    public ResponseEntity<WorkspaceResponseDTO> getWorkspaceById(@RequestParam int workspaceId) {
        return workspaceService.getWorkspaceById(workspaceId);
    }

    @PutMapping
    public ResponseEntity<Integer> updateWorkspace(@RequestBody WorkspaceUpdateDTO updateWorkspace) {
        if (updateWorkspace.getId() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return workspaceService.updateWorkspace(updateWorkspace);
    }

    @GetMapping("/workspaces")
    public ResponseEntity<WorkspaceSettingValueResponseDTO> getSettingValueByKeyNameWorkspaceId(
            @RequestParam String keyName, @RequestParam int workspaceId) {
        return workspaceSettingService.getSettingValueByKeyNameAndWorkspaceId(keyName, workspaceId);
    }

    @GetMapping("/members")
    public ResponseEntity<MemberWorkspaceResponseDTO> getMembersByWorkspace(@RequestParam int workspaceId) {
        return workspaceMemberService.getWorkspaceMembersByUserId(workspaceId);
    }
}
