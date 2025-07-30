package vn.ypp4.quanphan.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.util.constant.WorkspaceTypeEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Workspace {
    private int Id;
    private String Name;
    private String Description;
    private WorkspaceTypeEnum Type;
    private Instant CreatedAt;
    private int CreatedBy;

}
