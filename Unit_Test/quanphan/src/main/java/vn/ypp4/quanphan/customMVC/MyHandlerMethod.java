package vn.ypp4.quanphan.customMVC;
import lombok.Getter;
import java.lang.reflect.Method;

@Getter
public class MyHandlerMethod {
    private final Object controller;
    private final Method method;

    public MyHandlerMethod(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }
}

