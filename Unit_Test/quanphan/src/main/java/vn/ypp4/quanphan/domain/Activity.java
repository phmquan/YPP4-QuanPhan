package vn.ypp4.quanphan.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private int id;
    private LocalDateTime createdAt;
    private String activityDescription;
    private int userId;
    private int categoryId;
    private int ownerId;
}
