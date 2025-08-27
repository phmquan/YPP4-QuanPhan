package vn.ypp4.quanphan.mvc;

import org.junit.jupiter.api.Test;
import vn.ypp4.quanphan.customMVC.MyHandlerMapping;
import vn.ypp4.quanphan.customMVC.MyHandlerMethod;
import vn.ypp4.quanphan.customMVC.test.UserController;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TestMyHandlerMapping {
    @Test
    void testFindHandlerByUrl() throws Exception {
        MyHandlerMapping mapping = new MyHandlerMapping("vn.ypp4.quanphan");

        MyHandlerMethod handler = mapping.getHandler("/user");

        assertInstanceOf(UserController.class, handler.getController());
    }
}
