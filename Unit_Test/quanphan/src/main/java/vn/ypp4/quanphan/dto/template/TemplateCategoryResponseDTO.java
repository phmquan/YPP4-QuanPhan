package vn.ypp4.quanphan.dto.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.entity.TemplateCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateCategoryResponseDTO {
    private int id;
    private String displayValue;
    private String iconUrl;
    public TemplateCategoryResponseDTO(TemplateCategory templateCategory){
        this.id=templateCategory.getId();
        this.displayValue= templateCategory.getDisplayValue();
        this.iconUrl= templateCategory.getIconUrl();
    }


}
