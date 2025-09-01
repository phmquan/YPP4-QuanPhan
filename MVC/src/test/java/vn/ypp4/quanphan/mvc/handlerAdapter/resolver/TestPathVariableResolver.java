package vn.ypp4.quanphan.mvc.handlerAdapter.resolver;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyPathVariable;
import vn.ypp4.quanphan.mvc.handlerAdapter.resolver.PathVariableResolver;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPathVariableResolver {
    static class TestController {
        public void test(@MyPathVariable("id") int id) {
        }
    }

    @Test
    void testSupports() throws Exception {
        Method m = TestController.class.getMethod("test", int.class);
        Parameter param = m.getParameters()[0];

        PathVariableResolver resolver = new PathVariableResolver();

        assertTrue(resolver.supports(param));
    }

    @Test
    void testResolve() throws Exception {
        Method m = TestController.class.getMethod("test", int.class);
        Parameter param = m.getParameters()[0];
        Map<String, String> pathVars = Map.of("id", "42");
        PathVariableResolver resolver = new PathVariableResolver();

        Object result = resolver.resolve(param, new MockHttpServletRequest(), pathVars);

        assertEquals(42, result);
    }
}
