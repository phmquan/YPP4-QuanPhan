package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.customDI.annotation.MyAutowired;

import vn.ypp4.quanphan.customDI.context.MyApplicationContext;
import vn.ypp4.quanphan.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.customDI.core.MyBeanFactory;
import vn.ypp4.quanphan.customDI.metadata.MyAnnotationResolver;
import vn.ypp4.quanphan.customDI.test.OrderController;
import vn.ypp4.quanphan.customDI.test.OrderService;
import vn.ypp4.quanphan.customDI.test.OrderServiceImpl;
import vn.ypp4.quanphan.repository.board.UserBoardRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestBeanFactory {
    MyAnnotationResolver myAnnotationResolver = new MyAnnotationResolver();
    MyBeanFactory myBeanFactory = new MyBeanFactory();
    @Test
    void testRegisterBeanDefinition(){
        //Arrange

        MyBeanDefinition bean=new MyBeanDefinition(UserBoardRepository.class,"userBoardRepository","singleton");
        //Act
        myBeanFactory.registerBeanDefinition(bean);
        //Assert
        assertNotNull(myBeanFactory.getBean(UserBoardRepository.class));
    }
    @Test
    void testCreateOrGetBean(){
        MyBeanDefinition serviceBean = myAnnotationResolver.createBeanDefinition(OrderServiceImpl.class);
        Object service= myBeanFactory.createOrGetBean(serviceBean);
        myBeanFactory.registerBeanDefinition(serviceBean);
       //Arrange
        MyBeanDefinition bean=new MyBeanDefinition(OrderController.class,"orderController","singleton");
        //Act
        Object result=myBeanFactory.createOrGetBean(bean);

        Assertions.assertNotNull(result);
    }
    @Test
    void testInitializeSingleton(){
        // Register dependency
        myBeanFactory.registerBeanDefinition(
                new MyBeanDefinition(OrderServiceImpl.class, "orderServiceImpl","OrderService",null, "singleton")
        );

        // Register controller
        myBeanFactory.registerBeanDefinition(
                new MyBeanDefinition(OrderController.class,"orderController","singleton")
        );

        myBeanFactory.initializeSingletons();

        OrderController result = myBeanFactory.getBean(OrderController.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getOrderService());
    }
}
