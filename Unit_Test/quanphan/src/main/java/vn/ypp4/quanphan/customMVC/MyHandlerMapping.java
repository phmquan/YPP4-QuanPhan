package vn.ypp4.quanphan.customMVC;

import org.springframework.web.method.HandlerMethod;
import vn.ypp4.quanphan.customDI.annotation.MyRequestMapping;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHandlerMapping {
    private final Map<String, MyHandlerMethod> handlerMap = new HashMap<>();
    private final MyClassPathScanner scanner = new MyClassPathScanner();

    public MyHandlerMapping(String basePackage) throws Exception {
        scanControllers(basePackage);
    }

    private void scanControllers(String basePackage) throws Exception {

        Set<Class<?>> controllers = scanner.scanForControllers(basePackage);

        for (Class<?> controllerClass : controllers) {
            Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();

            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping mapping = method.getAnnotation(MyRequestMapping.class);
                    String path = mapping.value();
                    handlerMap.put(controllerClass.getAnnotation(MyRequestMapping.class).value()+path, new MyHandlerMethod(controllerInstance, method));
                }
            }
        }
    }

    public MyHandlerMethod getHandler(String uri) {
        return handlerMap.get(uri);
    }
}
