package vn.ypp4.quanphan.customDI.test;

import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customDI.annotation.MyController;


@MyController
@Getter
@Setter
public class OrderController {
    @MyAutowired
    private OrderService orderService;
    private String message;
    public String checkout(String message){
        return orderService.checkout(message);
    }
}
