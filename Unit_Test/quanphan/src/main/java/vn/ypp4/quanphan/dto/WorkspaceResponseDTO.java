package vn.ypp4.quanphan.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.Workspace;

@Getter
@Setter

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
