package vn.ypp4.quanphan.mvc;

import org.junit.jupiter.api.Test;
import vn.ypp4.quanphan.customDI.test.OrderController;
import vn.ypp4.quanphan.customMVC.MyHandlerMapping;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMyHandlerMapping {
    @Test
    void testFindHandlerByUrl() {
        MyHandlerMapping mapping = new MyHandlerMapping();
        mapping.register("/users", new OrderController());

        Object handler = mapping.getHandler("/users");

        assertTrue(handler instanceof OrderController);
    }
}
