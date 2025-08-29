package vn.ypp4.quanphan.api.service.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.api.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.api.repository.template.TemplateRepository;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateRepository templateRepository;
    @Override
    public List<TemplateCategoryResponseDTO> getTemplateCategories(int numCategoryRequest) {
        return templateRepository.findTemplateCategories(numCategoryRequest);
    }

    @Override
    public List<TemplateResponseDTO> getTemplate(int numTemplateRequest) {
        return templateRepository.findTemplate(numTemplateRequest);
    }

    @Override
    public TemplateResponseDTO getTemplateDetail(int templateId) {
        return templateRepository.findTemplateById(templateId);
    }
}
