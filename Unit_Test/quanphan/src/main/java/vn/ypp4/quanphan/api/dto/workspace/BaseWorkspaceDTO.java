package vn.ypp4.quanphan.api.dto.workspace;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseWorkspaceDTO {
    private int id;
    private String workspaceName;
    private String logoUrl;

    public BaseWorkspaceDTO(int id,String workspaceName, String logoUrl) {
        this.id= id;
        this.workspaceName = workspaceName;
        this.logoUrl = logoUrl;
    }
}

