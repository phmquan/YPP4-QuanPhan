package vn.ypp4.quanphan.service.template;

import vn.ypp4.quanphan.domain.dto.template.TemplateCategoryResponseDTO;

import vn.ypp4.quanphan.domain.dto.template.TemplateResponseDTO;

import java.util.List;

public interface TemplateService {
    public List<TemplateCategoryResponseDTO> getTemplateCategories(int numCategoryRequested);
    public List<TemplateResponseDTO> getTemplate(int numTemplateRequest);
    public TemplateResponseDTO getTemplateDetail(int templateId);
}
