package vn.ypp4.quanphan.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Color {
    private int id;
    private String colorName;
    private String colorHex;
    private String icon;
}
