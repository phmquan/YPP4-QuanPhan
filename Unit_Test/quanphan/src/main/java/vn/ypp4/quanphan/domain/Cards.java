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
public class Cards {
    private int id;
    private int stageId;
    private String title;
    private String cardDescription;
    private LocalDateTime createdAt;
    private int createdBy;
    private String cardStatus;
    private String cardLocation;
    private LocalDate startDate;
    private LocalDate dueDate;
    private int cardCoverTypeId;
    private String coverValue;
    private int position;
    private LocalDateTime updatedAt;
    private int updatedBy;
}
