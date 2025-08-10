package vn.ypp4.quanphan.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckListItem {
    private int id;
    private String checkListItemName;
    private int memberId;
    private int checkListId;
    private LocalDate dueDate;
    private boolean checkListItemStatus;
    private LocalDateTime createdAt;
    private int createdBy;
    private LocalDateTime updatedAt;
    private int updatedBy;
    private int position;
}
