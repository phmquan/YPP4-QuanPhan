package vn.ypp4.quanphan.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.TemplateController;
import vn.ypp4.quanphan.domain.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.domain.dto.template.TemplateDetailResponseDTO;
import vn.ypp4.quanphan.domain.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.domain.entity.Template;
import vn.ypp4.quanphan.domain.entity.TemplateCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TestTemplate {
    @Autowired
    private TemplateController templateController;
    @Test
    void getTemplateCategories_Success(){

        //Arrange
        int numCategoryRequest=3;
        List<TemplateCategoryResponseDTO> expected=new ArrayList<>();
        expected.add(new TemplateCategoryResponseDTO(1, "Marketing", "https://example.com/icons/marketing.png"));
        expected.add(new TemplateCategoryResponseDTO(2, "Design", "https://example.com/icons/design.png"));
        expected.add(new TemplateCategoryResponseDTO(3, "Finance", "https://example.com/icons/finance.png"));

        //Act
        List<TemplateCategoryResponseDTO> result=templateController.getTemplateCategories(numCategoryRequest);

        //Assert
        assertEquals(expected.size(),result.size());
    }
    @Test
    void getTemplate_Success(){
        //Arrange
        int numTemplateRequest=3;
        List<TemplateResponseDTO> expected=new ArrayList<>();
        expected.add(new TemplateResponseDTO(1, "Marketing Plan", "A basic marketing plan template",120,45, "https://example.com/bg/marketing.jpg", 1));
        expected.add(new TemplateResponseDTO(2, "Design Mockup", "Template for design mockups",80,30, "https://example.com/bg/design.jpg", 2));
        expected.add(new TemplateResponseDTO(3, "Financial Report", "Monthly financial report template",60,20, "https://example.com/bg/finance.jpg", 1));

        //Act
        List<TemplateResponseDTO> result=templateController.getTemplate(numTemplateRequest);

        //Assert
        assertEquals(expected.getFirst().getTitle(),result.getFirst().getTitle());
    }
    @Test
    void getTemplateDetail(){
        //Arrange
        int templateId=1;
        UserResponseDTO user = new UserResponseDTO(1, "quang", "Quang Nguyen", "pic1.jpg","quang@example.com");
        TemplateDetailResponseDTO expected = new TemplateDetailResponseDTO(
                new Template(5,"Marketing Plan", "A basic marketing plan template", 1, 120, 45, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1, "https://example.com/bg/marketing.jpg")
                ,user
        );
        //Act
        TemplateDetailResponseDTO result = templateController.getTemplateDetail(templateId);
        //Assert
        assertEquals(expected.getCreatedBy().getEmail(), result.getCreatedBy().getEmail());
    }
}
