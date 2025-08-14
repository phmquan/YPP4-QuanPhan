package vn.ypp4.quanphan.domain.dto.workspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceUpdateDTO {
    private int id;
    private String workspaceName;
    private String workspaceDescription;
    private LocalDateTime updatedAt;
    private int updatedBy;
}
