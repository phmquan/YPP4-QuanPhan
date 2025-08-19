package vn.ypp4.quanphan.customDI.test;

import vn.ypp4.quanphan.customDI.annotation.MyService;

@MyService
public class OrderService {

    public String checkout(String message) {
        return message;
    }
}
