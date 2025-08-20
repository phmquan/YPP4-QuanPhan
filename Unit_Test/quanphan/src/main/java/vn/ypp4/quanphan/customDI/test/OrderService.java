package vn.ypp4.quanphan.customDI.test;

import vn.ypp4.quanphan.customDI.annotation.MyService;

@MyService
public interface OrderService {
    String checkout(String message);
}
