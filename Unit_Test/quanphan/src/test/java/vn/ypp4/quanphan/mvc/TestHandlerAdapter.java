package vn.ypp4.quanphan.mvc;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import vn.ypp4.quanphan.customDI.annotation.MyPathVariable;
import vn.ypp4.quanphan.customDI.annotation.MyRequestParam;
import vn.ypp4.quanphan.customMVC.handlerAdapter.MyHandlerAdapter;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHandlerAdapter {
    public static class TestController {
        public String test(@MyPathVariable("id") int id,
                           @MyRequestParam("active") boolean active,
                           HttpServletRequest req) {
            return "id=" + id + ", active=" + active + ", ua=" + req.getHeader("User-Agent");
        }
    }

    @Test
    void testHandle() throws Exception {
        TestController controller = new TestController();
        Method method = TestController.class.getMethod("test", int.class, boolean.class, HttpServletRequest.class);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/42");
        request.setParameter("active", "true");
        request.addHeader("User-Agent", "JUnit");

        Map<String, String> pathVars = Map.of("id", "42");

        MyHandlerAdapter adapter = new MyHandlerAdapter(); // đã chứa 3 resolver
        Object result = adapter.handle(controller, method, request, pathVars);

        assertEquals("id=42, active=true, ua=JUnit", result);
    }
}
