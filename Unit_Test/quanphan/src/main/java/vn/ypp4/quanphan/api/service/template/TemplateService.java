package vn.ypp4.quanphan.api.service.template;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.api.dto.template.TemplateResponseDTO;

import java.util.List;

@Service
public interface TemplateService {
    List<TemplateCategoryResponseDTO> getTemplateCategories(int numCategoryRequest);

    List<TemplateResponseDTO> getTemplate(int numTemplateRequest);

    TemplateResponseDTO getTemplateDetail(int templateId);
}
