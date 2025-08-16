package vn.ypp4.quanphan.domain.dto.workspace;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

