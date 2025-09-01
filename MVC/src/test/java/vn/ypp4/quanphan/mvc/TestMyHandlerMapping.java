package vn.ypp4.quanphan.mvc;

import org.junit.jupiter.api.Test;
import vn.ypp4.quanphan.mvc.customDI.container.MyApplicationContext;
import vn.ypp4.quanphan.mvc.MyHandlerMapping;
import vn.ypp4.quanphan.mvc.MyHandlerMethod;
import vn.ypp4.quanphan.mvc.test.UserController;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyHandlerMapping {
    private final MyApplicationContext myApplicationContext = new MyApplicationContext("vn.ypp4.quanphan");

    @Test
    void testFindHandlerByUrlAndMethod() throws Exception {
        MyHandlerMapping mapping = new MyHandlerMapping("vn.ypp4.quanphan", myApplicationContext);

        MyHandlerMethod handler = mapping.getHandler("/user/detail", "POST");

        assertNotNull(handler);
        assertInstanceOf(UserController.class, handler.getController());
        assertEquals("detail", handler.getMethod().getName());
    }

    @Test
    void testHandlerForDefaultReturn() throws Exception {
        MyHandlerMapping mapping = new MyHandlerMapping("vn.ypp4.quanphan", myApplicationContext);

        MyHandlerMethod handler = mapping.getHandler("/user", "GET");

        assertNotNull(handler);
        assertEquals("defaultReturn", handler.getMethod().getName());
    }

    @Test
    void testHandlerForHello() throws Exception {
        MyHandlerMapping mapping = new MyHandlerMapping("vn.ypp4.quanphan", myApplicationContext);

        MyHandlerMethod handler = mapping.getHandler("/user/hello", "GET");

        assertNotNull(handler);
        assertEquals("hello", handler.getMethod().getName());
    }
}
