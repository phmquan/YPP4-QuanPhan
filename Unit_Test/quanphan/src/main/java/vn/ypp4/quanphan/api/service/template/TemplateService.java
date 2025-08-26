package vn.ypp4.quanphan.api.service.template;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.api.dto.template.TemplateResponseDTO;

import java.util.List;

@Service
public interface TemplateService {
    ResponseEntity<List<TemplateCategoryResponseDTO>> getTemplateCategories(int numCategoryRequest);

    ResponseEntity<List<TemplateResponseDTO>> getTemplate(int numTemplateRequest);

    ResponseEntity<TemplateResponseDTO> getTemplateDetail(int templateId);
}
