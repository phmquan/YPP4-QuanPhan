package vn.ypp4.quanphan.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ypp4.quanphan.api.dto.template.TemplateCategoryResponseDTO;

import vn.ypp4.quanphan.api.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.api.service.template.TemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping("/categories")
    public ResponseEntity<List<TemplateCategoryResponseDTO>> getTemplateCategories(
            @RequestParam int numCategoryRequest) {
        return templateService.getTemplateCategories(numCategoryRequest);
    }

    @GetMapping
    public ResponseEntity<List<TemplateResponseDTO>> getTemplate(@RequestParam int numTemplateRequest) {
        return templateService.getTemplate(numTemplateRequest);
    }

    @GetMapping("/detail")
    public ResponseEntity<TemplateResponseDTO> getTemplateDetail(@RequestParam int templateId) {
        return templateService.getTemplateDetail(templateId);
    }
}
