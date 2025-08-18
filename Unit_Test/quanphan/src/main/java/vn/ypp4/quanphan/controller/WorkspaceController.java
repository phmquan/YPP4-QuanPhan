package vn.ypp4.quanphan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.ypp4.quanphan.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceUpdateDTO;
import vn.ypp4.quanphan.service.workspace.WorkspaceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    @GetMapping("/member/{id}")
    public List<WorkspaceResponseDTO> getWorkspaceUserIsMember(@PathVariable int userId){
        return workspaceService.getMemberWorkspacesByUserId(userId);
    }
    @GetMapping("/{id}")
    public WorkspaceResponseDTO getWorkspaceById(@PathVariable int  workspaceId) {
        return workspaceService.getWorkspaceById(workspaceId);
    }
    @PutMapping
    public int updateWorkspace(WorkspaceUpdateDTO updateWorkspace) {
        if (updateWorkspace.getId() <= 0) {
            throw new IllegalArgumentException("Invalid workspace ID");
        }
        return workspaceService.updateWorkspace(updateWorkspace);
    }
}
