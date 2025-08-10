package vn.ypp4.quanphan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    private int id;
    private String permissionName;
    private String permissionCode;
}
