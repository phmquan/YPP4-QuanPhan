package vn.ypp4.quanphan.trello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.TemplateController;
import vn.ypp4.quanphan.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.dto.template.TemplateResponseDTO;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class TestTemplate {
    @Autowired
    private TemplateController templateController;
    @Test
    void getTemplateCategories_Success(){

        //Arrange
        int numCategoryRequest=3;

        //Act
        List<TemplateCategoryResponseDTO> result=templateController.getTemplateCategories(numCategoryRequest);

        //Assert
        assertNotNull(result);
    }
    @Test
    void getTemplate_Success(){
        //Arrange
        int numTemplateRequest=3;

        //Act
        List<TemplateResponseDTO> result=templateController.getTemplate(numTemplateRequest);

        //Assert
        assertNotNull(result);
    }
    @Test
    void getTemplateDetail(){
        //Arrange
        int templateId=1;
        //Act
        TemplateResponseDTO result = templateController.getTemplateDetail(templateId);
        //Assert
        Assertions.assertNotNull(result);
    }
}
