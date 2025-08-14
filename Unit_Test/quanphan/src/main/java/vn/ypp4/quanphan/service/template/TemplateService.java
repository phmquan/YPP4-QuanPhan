package vn.ypp4.quanphan.service.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.domain.dto.template.TemplateDetailResponseDTO;
import vn.ypp4.quanphan.domain.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.repository.TemplateRepository;
import vn.ypp4.quanphan.util.exception.TemplateNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
    public List<TemplateCategoryResponseDTO> getTemplateCategories(int numCategoryRequested) {
        return templateRepository.findCategory(numCategoryRequested)
                .stream()
                .filter(Objects::nonNull)
                .map(TemplateCategoryResponseDTO::new)
                .toList();
    }

    public List<TemplateResponseDTO> getTemplate(int numTemplateRequest) {
        return templateRepository.getTemplate(numTemplateRequest)
                .stream()
                .filter(Objects::nonNull)
                .map(TemplateResponseDTO::new)
                .toList();
    }

    public TemplateDetailResponseDTO getTemplateDetail(int templateId) {
        if (!templateRepository.existsById(templateId)) {
            throw new TemplateNotFoundException("Template with ID " + templateId + " not found.");
        }
        return templateRepository.getTemplateDetail(templateId);
    }
}
