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
public class CardSticker {
    private int cardId;
    private int stickerId;
    private LocalDateTime createdAt;
    private int createdBy;
    private float positionX;
    private float positionY;
    private int indexZ;
}
