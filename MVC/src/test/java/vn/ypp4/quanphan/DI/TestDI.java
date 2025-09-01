package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.mvc.customDI.container.MyApplicationContext;
import vn.ypp4.quanphan.mvc.customDI.test.OrderController;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestDI {
    final MyApplicationContext context = new MyApplicationContext("vn.ypp4.quanphan.customDI");

    @Test
    void testDIWithoutAutowired() {
        OrderController orderController = context.getBean(OrderController.class);
        assertEquals("hehe", orderController.checkout("hehe"));
    }

}
