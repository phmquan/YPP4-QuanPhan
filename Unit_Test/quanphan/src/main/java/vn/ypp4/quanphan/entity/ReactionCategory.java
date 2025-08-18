package vn.ypp4.quanphan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionCategory {
    private int id;
    private String categoryValue;
    private String displayValue;
}
