package vn.ypp4.quanphan.repository.template;

import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.dto.template.TemplateResponseDTO;

import java.util.List;

@Repository
public interface TemplateRepository {

    List<TemplateCategoryResponseDTO> findTemplateCategories(int numCategoryRequest);

    List<TemplateResponseDTO> findTemplate(int numTemplateRequest);

    TemplateResponseDTO findTemplateById(int templateId);
}
