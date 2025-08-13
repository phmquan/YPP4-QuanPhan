package vn.ypp4.quanphan.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryType {
    private int Id;
    private String CategoryTypeValue;
}
