package vn.ypp4.quanphan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PowerUp {
    private int id;
    private String powerUpName;
    private String iconUrl;
    private String backgroundUrl;
    private String authorName;
    private String powerUpDescription;
    private String emailContact;
    private String policyUrl;
    private Boolean isStaffPick;
    private Boolean isIntegration;
    private int categoryId;
}
