package vn.ypp4.quanphan.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
    private int id;
    private String reactionsName;
    private String shortCode;
    private int categoryId;
    private String icon;
}
