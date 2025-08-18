package vn.ypp4.quanphan.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Members {
    private int id;
    private int userId;
    private int rolePermissionId;
    private int ownerTypeId;
    private int ownerId;
    private int invitedBy;
    private LocalDateTime joinedAt;
    private String memberStatus;
}
