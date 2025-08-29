package vn.ypp4.quanphan.api.dto.workspace;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.api.entity.Workspace;

@Getter
@Setter
@NoArgsConstructor
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
