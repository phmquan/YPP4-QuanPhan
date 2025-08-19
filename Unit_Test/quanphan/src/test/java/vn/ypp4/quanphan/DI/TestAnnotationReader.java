package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.customDI.metadata.MyAnnotationReader;
import vn.ypp4.quanphan.customDI.test.OrderController;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestAnnotationReader {
    @Test
    void getBeanDefinitionName(){
        MyAnnotationReader reader =new MyAnnotationReader();

        MyBeanDefinition result=reader.createBeanDefinition(OrderController.class);

        assertEquals("orderController", result.getBeanName());
        assertEquals("hehe", result.getQualifier());
    }
}
