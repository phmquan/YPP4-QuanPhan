package vn.ypp4.quanphan.api.service.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TemplateCategoryResponseDTO>> getTemplateCategories(int numCategoryRequest) {
        try {
            List<TemplateCategoryResponseDTO> categories = templateRepository
                    .findTemplateCategories(numCategoryRequest);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<TemplateResponseDTO>> getTemplate(int numTemplateRequest) {
        try {
            List<TemplateResponseDTO> templates = templateRepository.findTemplate(numTemplateRequest);
            return ResponseEntity.ok(templates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<TemplateResponseDTO> getTemplateDetail(int templateId) {
        try {
            TemplateResponseDTO template = templateRepository.findTemplateById(templateId);
            if (template != null) {
                return ResponseEntity.ok(template);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
