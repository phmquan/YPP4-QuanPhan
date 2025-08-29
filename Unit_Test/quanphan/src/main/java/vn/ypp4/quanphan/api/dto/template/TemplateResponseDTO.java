package vn.ypp4.quanphan.api.dto.template;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TemplateResponseDTO extends BaseTemplateDTO {
    private int copied;
    private int viewed;
    private String authorName;
    private String authorAvatar;

    public TemplateResponseDTO(int id, String title, String templateDescription, String backgroundUrl, int copied, int viewed, String authorName, String authorAvatar) {
        super(id, title, templateDescription, backgroundUrl);
        this.copied = copied;
        this.viewed = viewed;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
    }
}
