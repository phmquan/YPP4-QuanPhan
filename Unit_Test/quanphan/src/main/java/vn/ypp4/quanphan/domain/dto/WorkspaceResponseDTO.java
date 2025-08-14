package vn.ypp4.quanphan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.entity.Workspace;

@Getter
@Setter
@AllArgsConstructor
public class WorkspaceResponseDTO {
    public WorkspaceResponseDTO() {
    }

    public WorkspaceResponseDTO(Workspace workspace){
        this.workspaceId=workspace.getId();
        this.workspaceName=workspace.getWorkspaceName();
        this.workspaceDescription=workspace.getWorkspaceDescription();
        this.workspaceLogo=workspace.getLogoUrl();
    }
    private int workspaceId;
    private String workspaceName;
    private String workspaceDescription;
    private String workspaceLogo;
}
