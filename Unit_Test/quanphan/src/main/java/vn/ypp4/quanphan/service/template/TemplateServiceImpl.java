package vn.ypp4.quanphan.service.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.domain.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.repository.TemplateRepository;
import vn.ypp4.quanphan.service.dto.EntityToDtoMapper;
import vn.ypp4.quanphan.util.exception.TemplateNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    @Override
    public List<TemplateCategoryResponseDTO> getTemplateCategories(int numCategoryRequested) {
        return EntityToDtoMapper.mapToDto(templateRepository.findCategory(numCategoryRequested), TemplateCategoryResponseDTO::new);
    }

    @Override
    public List<TemplateResponseDTO> getTemplate(int numTemplateRequest) {
        return templateRepository.getTemplate(numTemplateRequest);
    }

    @Override
    public TemplateResponseDTO getTemplateDetail(int templateId) {
        if (!templateRepository.existsById(templateId)) {
            throw new TemplateNotFoundException("Template with ID " + templateId + " not found.");
        }
        return templateRepository.getTemplateDetail(templateId);
    }
}
