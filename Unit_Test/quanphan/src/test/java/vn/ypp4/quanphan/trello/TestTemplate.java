package vn.ypp4.quanphan.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.ypp4.quanphan.api.controller.TemplateController;
import vn.ypp4.quanphan.api.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.api.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.api.service.template.TemplateService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestTemplate {

    @Mock
    private TemplateService templateService;

    @InjectMocks
    private TemplateController templateController;

    private TemplateCategoryResponseDTO sampleCategory;
    private List<TemplateCategoryResponseDTO> sampleCategories;
    private TemplateResponseDTO sampleTemplate;
    private List<TemplateResponseDTO> sampleTemplates;

    @BeforeEach
    void setUp() {
        sampleCategory = new TemplateCategoryResponseDTO();
        // Set sample data - adjust according to your DTO structure

        sampleCategories = Arrays.asList(sampleCategory, sampleCategory, sampleCategory);

        sampleTemplate = new TemplateResponseDTO();
        sampleTemplates = Arrays.asList(sampleTemplate, sampleTemplate, sampleTemplate);
    }

    @Test
    void getTemplateCategories_Success() {
        // Arrange
        int numCategoryRequest = 3;
        ResponseEntity<List<TemplateCategoryResponseDTO>> expectedResponse = ResponseEntity.ok(sampleCategories);
        when(templateService.getTemplateCategories(numCategoryRequest)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<TemplateCategoryResponseDTO>> result = templateController
                .getTemplateCategories(numCategoryRequest);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(3, result.getBody().size());
        verify(templateService, times(1)).getTemplateCategories(numCategoryRequest);
    }

    @Test
    void getTemplate_Success() {
        // Arrange
        int numTemplateRequest = 3;
        ResponseEntity<List<TemplateResponseDTO>> expectedResponse = ResponseEntity.ok(sampleTemplates);
        when(templateService.getTemplate(numTemplateRequest)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<TemplateResponseDTO>> result = templateController.getTemplate(numTemplateRequest);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(3, result.getBody().size());
        verify(templateService, times(1)).getTemplate(numTemplateRequest);
    }

    @Test
    void getTemplateDetail_Success() {
        // Arrange
        int templateId = 1;
        ResponseEntity<TemplateResponseDTO> expectedResponse = ResponseEntity.ok(sampleTemplate);
        when(templateService.getTemplateDetail(templateId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<TemplateResponseDTO> result = templateController.getTemplateDetail(templateId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(templateService, times(1)).getTemplateDetail(templateId);
    }

    @Test
    void getTemplateDetail_NotFound() {
        // Arrange
        int templateId = 999;
        ResponseEntity<TemplateResponseDTO> expectedResponse = ResponseEntity.notFound().build();
        when(templateService.getTemplateDetail(templateId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<TemplateResponseDTO> result = templateController.getTemplateDetail(templateId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(templateService, times(1)).getTemplateDetail(templateId);
    }

    @Test
    void getTemplateCategories_InternalServerError() {
        // Arrange
        int numCategoryRequest = 3;
        ResponseEntity<List<TemplateCategoryResponseDTO>> expectedResponse = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        when(templateService.getTemplateCategories(numCategoryRequest)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<TemplateCategoryResponseDTO>> result = templateController
                .getTemplateCategories(numCategoryRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(templateService, times(1)).getTemplateCategories(numCategoryRequest);
    }

    @Test
    void getTemplate_EmptyList() {
        // Arrange
        int numTemplateRequest = 0;
        List<TemplateResponseDTO> emptyList = Arrays.asList();
        ResponseEntity<List<TemplateResponseDTO>> expectedResponse = ResponseEntity.ok(emptyList);
        when(templateService.getTemplate(numTemplateRequest)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<TemplateResponseDTO>> result = templateController.getTemplate(numTemplateRequest);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getBody().size());
        verify(templateService, times(1)).getTemplate(numTemplateRequest);
    }
}
