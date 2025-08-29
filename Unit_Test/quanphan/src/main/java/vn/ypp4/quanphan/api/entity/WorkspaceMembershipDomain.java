package vn.ypp4.quanphan.api.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMembershipDomain {
    private int id;
    private int workspaceId;
    private String domain;
    private LocalDateTime createdAt;
}
