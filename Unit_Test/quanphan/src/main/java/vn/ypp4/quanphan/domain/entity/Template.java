package vn.ypp4.quanphan.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Template {
    private int id;
    private String title;
    private String templateDescription;
    private int categoryId;
    private int viewed;
    private int copied;
    private int createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int updatedBy;
    private int boardId;
    private String backgroundUrl;
}
