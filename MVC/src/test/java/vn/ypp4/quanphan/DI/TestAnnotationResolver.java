package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.mvc.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.mvc.customDI.metadata.MyAnnotationResolver;
import vn.ypp4.quanphan.mvc.customDI.test.OrderController;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestAnnotationResolver {
    @Test
    void getBeanDefinitionName() {
        MyAnnotationResolver reader = new MyAnnotationResolver();

        MyBeanDefinition result = reader.createBeanDefinition(OrderController.class);

        assertEquals("orderController", result.beanName());

    }
}
