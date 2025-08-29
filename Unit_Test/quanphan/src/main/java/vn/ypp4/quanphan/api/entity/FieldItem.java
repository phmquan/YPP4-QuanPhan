package vn.ypp4.quanphan.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldItem {
    private int id;
    private int colorId;
    private String fieldItemValue;
    private int position;
    private int customFieldId;
}
