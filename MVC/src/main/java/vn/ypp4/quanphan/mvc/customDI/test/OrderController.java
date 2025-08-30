package vn.ypp4.quanphan.customMVC.customDI.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyController;

@MyController
@Getter
@Setter
@NoArgsConstructor
public class OrderController {
    @MyAutowired
    private OrderService orderService;
    private String message;

    public String checkout(String message){
        return orderService.checkout(message);
    }
}
