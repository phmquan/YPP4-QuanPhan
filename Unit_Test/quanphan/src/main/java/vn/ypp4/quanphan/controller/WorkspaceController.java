package vn.ypp4.quanphan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ypp4.quanphan.domain.dto.WorkspaceResponseDTO;
import vn.ypp4.quanphan.service.workspace.WorkspaceService;

import java.util.Iterator;
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
}
