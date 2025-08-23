package vn.ypp4.quanphan.customMVC;

import vn.ypp4.quanphan.customDI.test.OrderController;

import java.util.HashMap;
import java.util.Map;

public class MyHandlerMapping {
    Map<String,Object> handlerMap = new HashMap<>();
    public void register(String s, OrderController orderController) {
        handlerMap.put(s,orderController);
    }

    public Object getHandler(String s) {
        return handlerMap.get(s);
    }
}
