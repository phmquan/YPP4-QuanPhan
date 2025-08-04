package vn.ypp4.quanphan.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Workspace {
    private int Id;
    private String WorkspaceName;
    private String WorkspaceDescription;
    private int CategoryId;
    private Instant CreatedAt;
    private int CreatedBy;
    private Instant UpdatedAt;
    private int UpdatedBy;
    private String LogoUrl;
}
