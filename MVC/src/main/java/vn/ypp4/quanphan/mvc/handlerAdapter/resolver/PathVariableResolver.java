package vn.ypp4.quanphan.mvc.handlerAdapter.resolver;

import jakarta.servlet.http.HttpServletRequest;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyPathVariable;

import java.lang.reflect.Parameter;
import java.util.Map;

import static org.apache.tomcat.util.IntrospectionUtils.convert;

public class PathVariableResolver implements ArgumentResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(MyPathVariable.class);
    }

    @Override
    public Object resolve(Parameter parameter, HttpServletRequest request, Map<String, String> pathVars) {
        String name = parameter.getAnnotation(MyPathVariable.class).value();
        String value = pathVars.get(name);
        return convert(value, parameter.getType());
    }
}
