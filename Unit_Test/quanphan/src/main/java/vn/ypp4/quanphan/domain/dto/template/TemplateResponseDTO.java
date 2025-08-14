package vn.ypp4.quanphan.domain.dto.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.entity.Template;

@Getter
@Setter
@AllArgsConstructor
public class TemplateResponseDTO {
    private int id;
    private String title;
    private String templateDescription ;
    private int viewed;
    private int copied;
    private String backgroundUrl;
    private int createdBy;
    public TemplateResponseDTO(Template template){
        this.id= template.getId();
        this.title=template.getTitle();
        this.templateDescription=template.getTemplateDescription();
        this.viewed=template.getViewed();
        this.copied=template.getCopied();
        this.backgroundUrl=template.getBackgroundUrl();
        this.createdBy=template.getCreatedBy();
    }

}
