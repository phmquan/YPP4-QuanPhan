package vn.ypp4.quanphan.dto.template;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseTemplateDTO {
    private int id;
    private String title;
    private String templateDescription;
    private String backgroundUrl;

    public BaseTemplateDTO(int id, String title, String templateDescription, String backgroundUrl) {
        this.id = id;
        this.title = title;
        this.templateDescription = templateDescription;
        this.backgroundUrl = backgroundUrl;
    }
}

