package vn.ypp4.quanphan.mvc.customDI.test;

import vn.ypp4.quanphan.mvc.customDI.annotation.MyService;

@MyService
public interface OrderService {
    String checkout(String message);
}
