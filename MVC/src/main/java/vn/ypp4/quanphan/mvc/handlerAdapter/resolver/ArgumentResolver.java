package vn.ypp4.quanphan.mvc.handlerAdapter.resolver;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.Map;

public interface ArgumentResolver {
    boolean supports(Parameter parameter);
    Object resolve(Parameter parameter, HttpServletRequest request, Map<String, String> pathVars);
}

