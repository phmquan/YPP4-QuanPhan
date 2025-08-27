package vn.ypp4.quanphan.customMVC.handlerAdapter;
import jakarta.servlet.http.HttpServletRequest;
import vn.ypp4.quanphan.customMVC.MyHandlerMethod;
import vn.ypp4.quanphan.customMVC.handlerAdapter.resolver.ArgumentResolver;
import vn.ypp4.quanphan.customMVC.handlerAdapter.resolver.PathVariableResolver;
import vn.ypp4.quanphan.customMVC.handlerAdapter.resolver.RequestParamResolver;
import vn.ypp4.quanphan.customMVC.handlerAdapter.resolver.ServletRequestResolver;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MyHandlerAdapter {
    private final List<ArgumentResolver> resolvers = List.of(
            new RequestParamResolver(),
            new PathVariableResolver(),
            new ServletRequestResolver()
    );

    public Object handle(Object controller,
                         Method method,
                         HttpServletRequest request,
                         Map<String, String> pathVars) throws Exception {
        Object[] args = Arrays.stream(method.getParameters())
                .map(param -> resolvers.stream()
                        .filter(r -> r.supports(param))
                        .findFirst()
                        .map(r -> r.resolve(param, request, pathVars))
                        .orElse(null))
                .toArray();
        return method.invoke(controller, args);
    }
}
