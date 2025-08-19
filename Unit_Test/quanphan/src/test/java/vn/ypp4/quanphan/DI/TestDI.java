package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customDI.annotation.MyQualifier;
import vn.ypp4.quanphan.customDI.context.MyApplicationContext;
import vn.ypp4.quanphan.customDI.test.OrderController;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestDI {

    @Test
    void testDI() {
        MyApplicationContext context= new MyApplicationContext("vn.ypp4.quanphan");
        OrderController orderController= context.getBean(OrderController.class);
        assertEquals("hehe", orderController.checkout("hehe"));

    }
}
