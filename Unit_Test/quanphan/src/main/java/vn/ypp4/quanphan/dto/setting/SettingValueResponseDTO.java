package vn.ypp4.quanphan.dto.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingValueResponseDTO {
    private int id;
    private int ownerId;
    private String settingKey;
    private String displayValue;
}
