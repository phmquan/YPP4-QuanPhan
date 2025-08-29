package vn.ypp4.quanphan.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentType {
    private int id;
    private String typeValue;
    private String displayValue;
}
