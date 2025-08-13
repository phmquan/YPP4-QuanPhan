package vn.ypp4.quanphan.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareLink {
    private int id;
    private int ownerTypeId;
    private int rolePermissionId;
    private int ownerId;
    private String shareLinkToken;
    private boolean shareLinkStatus;
}
