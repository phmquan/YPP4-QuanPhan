package vn.ypp4.quanphan.domain.dto.template;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.domain.entity.Template;

@Getter
@Setter
@NoArgsConstructor
public class TemplateDetailResponseDTO {
    private int templateId;
    private String title;
    private String templateDescription;
    private int copied;
    private int viewed;
    private UserResponseDTO createdBy;

    public TemplateDetailResponseDTO(Template template,UserResponseDTO userResponseDTO){
        this.templateId = template.getId();
        this.title = template.getTitle();
        this.templateDescription = template.getTemplateDescription();
        this.copied = template.getCopied();
        this.viewed = template.getViewed();
        this.createdBy = userResponseDTO;
    }
}
