package vn.ypp4.quanphan.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {
    private int Id;
    private String CategoryName;
    private String CategoryDescription;
    private int CategoryTypeId;
    private Instant CreatedAt;
    private int CreatedBy;
    private String Icon;
    private int Position;
    private boolean IsActive;
}
