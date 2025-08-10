package vn.ypp4.quanphan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingKey {
    private int id;
    private String keyName;
    private String settingKeyDescription;
    private int ownerTypeId;
    private int defaultValue;
    private boolean isBoolean;
}
