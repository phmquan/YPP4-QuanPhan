package vn.ypp4.quanphan.domain.dto.stage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class StageDetailDTO {
    private int cardId;
    private String cardTitle;
    private int stageId;
    private String coverValue;
    private String colorName;
    private String attachmentPath;
    private int position;
    private String cardDescription;
}
