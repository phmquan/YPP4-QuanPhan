package vn.ypp4.quanphan.customMVC;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyRequestMapping;
import vn.ypp4.quanphan.customMVC.customDI.container.MyApplicationContext;
import vn.ypp4.quanphan.customMVC.customDI.scanner.MyClassPathScanner;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHandlerMapping {
    private final Map<String, MyHandlerMethod> handlerMap = new HashMap<>();
    private final MyClassPathScanner scanner = new MyClassPathScanner();
    private  MyApplicationContext myApplicationContext;
    /***
     * Scan through class and get all class with @MyController annotation and which each method in class, iterate through all method with @MyRequestParam
     * and save to a Hashmap
     * @param basePackage
     */
    public MyHandlerMapping(String basePackage,MyApplicationContext myApplicationContext)  {
        scanControllers(basePackage,myApplicationContext);
    }

    private void scanControllers(String basePackage,MyApplicationContext myApplicationContext)  {
        Set<Class<?>> controllers = scanner.scanForControllers(basePackage);

        for (Class<?> controllerClass : controllers) {
            Object controllerInstance = myApplicationContext.getBean(controllerClass);

            String basePath = "";
            if (controllerClass.isAnnotationPresent(MyRequestMapping.class)) {
                basePath = controllerClass.getAnnotation(MyRequestMapping.class).value();
            }

            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping mapping = method.getAnnotation(MyRequestMapping.class);
                    String fullPath = basePath + mapping.value();
                    String key = mapping.method().toString() + ":" + fullPath;
                    handlerMap.put(key, new MyHandlerMethod(controllerInstance, method));
                }
            }
        }
    }

    public MyHandlerMethod getHandler(String uri, String httpMethod) {
        return handlerMap.get(httpMethod + ":" + uri);
    }
}
