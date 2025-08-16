package vn.ypp4.quanphan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.ypp4.quanphan.domain.dto.template.TemplateCategoryResponseDTO;

import vn.ypp4.quanphan.domain.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.service.template.TemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping("/categories")
    public List<TemplateCategoryResponseDTO> getTemplateCategories(@RequestParam int numCategoryRequest){
        return templateService.getTemplateCategories(numCategoryRequest);
    }

    public List<TemplateResponseDTO> getTemplate(int numTemplateRequest) {
        return templateService.getTemplate(numTemplateRequest);
    }

    public TemplateResponseDTO getTemplateDetail(int templateId) {
        return templateService.getTemplateDetail(templateId);
    }
}
