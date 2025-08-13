package vn.ypp4.quanphan.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingOption {
    private int id;
    private String displayValue;
    private String settingOptionValue;
}
