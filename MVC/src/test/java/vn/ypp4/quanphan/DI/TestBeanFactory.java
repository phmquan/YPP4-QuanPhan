package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import vn.ypp4.quanphan.mvc.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.mvc.customDI.core.MyBeanFactory;
import vn.ypp4.quanphan.mvc.customDI.metadata.MyAnnotationResolver;
import vn.ypp4.quanphan.mvc.customDI.test.OrderController;
import vn.ypp4.quanphan.mvc.customDI.test.OrderServiceImpl;
// import vn.ypp4.quanphan.api.repository.board.UserBoardRepository;

@SpringBootTest
public class TestBeanFactory {
    MyAnnotationResolver myAnnotationResolver = new MyAnnotationResolver();
    MyBeanFactory myBeanFactory = new MyBeanFactory();

    @Test
    void testCreateOrGetBean() {
        MyBeanDefinition serviceBean = myAnnotationResolver.createBeanDefinition(OrderServiceImpl.class);
        Object service = myBeanFactory.createOrGetBean(serviceBean);
        myBeanFactory.registerBeanDefinition(serviceBean);
        // Arrange
        MyBeanDefinition bean = new MyBeanDefinition(OrderController.class, "orderController", "singleton");
        // Act
        Object result = myBeanFactory.createOrGetBean(bean);

        Assertions.assertNotNull(result);
    }

    @Test
    void testInitializeSingleton() {
        // Register dependency
        myBeanFactory.registerBeanDefinition(
                new MyBeanDefinition(OrderServiceImpl.class, "orderServiceImpl", "OrderService", null, "singleton"));

        // Register controller
        myBeanFactory.registerBeanDefinition(
                new MyBeanDefinition(OrderController.class, "orderController", "singleton"));

        myBeanFactory.initializeSingletons();

        OrderController result = myBeanFactory.getBean(OrderController.class);
        Assertions.assertNotNull(result);
        // Assertions.assertNotNull(result.getOrderService());
    }
}
