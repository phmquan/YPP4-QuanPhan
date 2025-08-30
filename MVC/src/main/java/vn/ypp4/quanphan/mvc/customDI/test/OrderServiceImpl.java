package vn.ypp4.quanphan.mvc.customDI.test;

import vn.ypp4.quanphan.mvc.customDI.annotation.MyService;

@MyService
public class OrderServiceImpl implements OrderService {
    public String checkout(String message) {
        return message;
    }
}
