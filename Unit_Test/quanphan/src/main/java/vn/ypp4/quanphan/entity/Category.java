package vn.ypp4.quanphan.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int id;
    private String categoryName;
    private String categoryDescription;
    private int categoryTypeId;
    private LocalDateTime createdAt;
    private int createdBy;
    private String icon;
    private int position;
    private boolean isActive;
}
