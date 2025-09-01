package vn.ypp4.quanphan.mvc.handlerAdapter.resolver;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyRequestParam;
import vn.ypp4.quanphan.mvc.handlerAdapter.resolver.RequestParamResolver;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRequestParamResolver {
    static class TestController {
        public void test(@MyRequestParam("name") String name) {
        }
    }

    @Test
    void testSupports() throws Exception {
        Method m = TestController.class.getMethod("test", String.class);
        Parameter param = m.getParameters()[0];
        RequestParamResolver resolver = new RequestParamResolver();
        assertTrue(resolver.supports(param));
    }

    @Test
    void testResolve() throws Exception {
        Method m = TestController.class.getMethod("test", String.class);
        Parameter param = m.getParameters()[0];
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "Quan");
        RequestParamResolver resolver = new RequestParamResolver();

        Object result = resolver.resolve(param, request, Map.of());

        assertEquals("Quan", result);
    }
}
