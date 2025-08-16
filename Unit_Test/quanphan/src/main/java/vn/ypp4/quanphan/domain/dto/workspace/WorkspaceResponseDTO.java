package vn.ypp4.quanphan.domain.dto.workspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.entity.Workspace;

@Getter
@Setter

public class WorkspaceResponseDTO extends BaseWorkspaceDTO {

    private String workspaceDescription;

    public WorkspaceResponseDTO(Workspace workspace){
        super(workspace.getId(), workspace.getWorkspaceName(), workspace.getLogoUrl());
        this.setWorkspaceName(workspace.getWorkspaceName());
        this.setLogoUrl(workspace.getLogoUrl());
        this.workspaceDescription = workspace.getWorkspaceDescription();
    }


    public WorkspaceResponseDTO(String workspaceName, String logoUrl, int id, String workspaceDescription) {
        super(id,workspaceName, logoUrl);
        this.workspaceDescription = workspaceDescription;
    }
}
