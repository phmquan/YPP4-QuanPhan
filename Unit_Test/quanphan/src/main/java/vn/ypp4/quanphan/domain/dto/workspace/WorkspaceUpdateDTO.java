package vn.ypp4.quanphan.domain.dto.workspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class WorkspaceUpdateDTO {
    private int id;
    private String workspaceName;
    private String workspaceDescription;
    private LocalDateTime updatedAt;
    private int updatedBy;

    public WorkspaceUpdateDTO(int id, String workspaceName, String workspaceDescription, LocalDateTime updatedAt, int updatedBy) {
        this.id = id;
        this.workspaceName = workspaceName;
        this.workspaceDescription = workspaceDescription;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
}
