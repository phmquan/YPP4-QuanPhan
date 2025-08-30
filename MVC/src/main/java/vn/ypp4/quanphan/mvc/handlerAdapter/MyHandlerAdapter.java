package vn.ypp4.quanphan.mvc.handlerAdapter;
import jakarta.servlet.http.HttpServletRequest;
import vn.ypp4.quanphan.mvc.view.ModelAndView;
import vn.ypp4.quanphan.mvc.handlerAdapter.resolver.ArgumentResolver;
import vn.ypp4.quanphan.mvc.handlerAdapter.resolver.PathVariableResolver;
import vn.ypp4.quanphan.mvc.handlerAdapter.resolver.RequestParamResolver;
import vn.ypp4.quanphan.mvc.handlerAdapter.resolver.ServletRequestResolver;
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

    public ModelAndView handle(Object controller,
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

        Object returnValue= method.invoke(controller, args);

        if (returnValue instanceof ModelAndView) {
            return (ModelAndView) returnValue;
        } else if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue);
        } else {
            ModelAndView mv = new ModelAndView("jsonView");
            mv.addObject("data", returnValue);
            return mv;
        }
    }
}
