package vn.ypp4.quanphan.api.dto.workspace;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkspaceSettingValueResponseDTO {
    private int id;
    private int workspaceId;
    private String settingKey;
    private String displayValue;

    public WorkspaceSettingValueResponseDTO(int id, int workspaceId, String settingKey, String displayValue) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.settingKey = settingKey;
        this.displayValue = displayValue;
    }
}
