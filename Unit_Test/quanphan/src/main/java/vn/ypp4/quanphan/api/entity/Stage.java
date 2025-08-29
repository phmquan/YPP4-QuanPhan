package vn.ypp4.quanphan.api.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stage {
    private int id;
    private String title;
    private LocalDateTime createdAt;
    private int createdBy;
    private int boardId;
    private String stageStatus;
    private int colorId;
    private int position;
    private LocalDateTime updatedAt;
    private int updatedBy;
}
