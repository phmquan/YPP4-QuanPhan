package vn.ypp4.quanphan.mvc.handlerAdapter.resolver;

import jakarta.servlet.http.HttpServletRequest;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyRequestParam;
import java.lang.reflect.Parameter;
import java.util.Map;
import static org.apache.tomcat.util.IntrospectionUtils.convert;

public class RequestParamResolver implements ArgumentResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(MyRequestParam.class);
    }

    @Override
    public Object resolve(Parameter parameter, HttpServletRequest request, Map<String, String> pathVars) {
        MyRequestParam ann = parameter.getAnnotation(MyRequestParam.class);
        String name = ann.value();
        if (name == null || name.isEmpty()) {
            name = parameter.getName();
        }
        String value = request.getParameter(name);
        return convert(value, parameter.getType());
    }
}
