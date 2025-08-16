package vn.ypp4.quanphan.domain.dto.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseCardDTO {
    private int cardId;
    private String cardTitle;
    private int stageId;
    private String coverValue;
    private String colorName;
    private String attachmentPath;
    private int position;
    private String cardDescription;
}

