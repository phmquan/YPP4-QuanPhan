package vn.ypp4.quanphan.mvc.handlerAdapter.resolver;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.Map;

public class ServletRequestResolver implements ArgumentResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(HttpServletRequest.class);
    }

    @Override
    public Object resolve(Parameter parameter, HttpServletRequest request, Map<String, String> pathVars) {
        return request;
    }
}
