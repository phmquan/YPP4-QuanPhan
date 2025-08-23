package vn.ypp4.quanphan.mvc.handlerAdapter.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import vn.ypp4.quanphan.customMVC.handlerAdapter.resolver.ServletRequestResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestServletRequestResolver {
    static class TestController {
        public void test(HttpServletRequest request) {}
    }

    @Test
    void testSupports() throws Exception {
        Method m = TestController.class.getMethod("test", HttpServletRequest.class);
        Parameter param = m.getParameters()[0];

        ServletRequestResolver resolver = new ServletRequestResolver();

        assertTrue(resolver.supports(param));
    }

    @Test
    void testResolve() throws Exception {
        Method m = TestController.class.getMethod("test", HttpServletRequest.class);
        Parameter param = m.getParameters()[0];
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestResolver resolver = new ServletRequestResolver();
        Object result = resolver.resolve(param, request, Map.of());
        assertSame(request, result);
    }
}
