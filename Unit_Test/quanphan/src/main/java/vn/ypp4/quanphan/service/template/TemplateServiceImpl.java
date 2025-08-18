package vn.ypp4.quanphan.service.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.dto.template.TemplateResponseDTO;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private final TemplateRepository templateRepository;
    @Override
    public List<TemplateCategoryResponseDTO> getTemplateCategories(int numCategoryRequest) {
        return List.of();
    }

    @Override
    public List<TemplateResponseDTO> getTemplate(int numTemplateRequest) {
        return List.of();
    }

    @Override
    public TemplateResponseDTO getTemplateDetail(int templateId) {
        return null;
    }
}
