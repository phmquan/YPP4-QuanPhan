package vn.ypp4.quanphan.customDI.test;

import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.customDI.annotation.MyController;

@MyController
@Getter
@Setter
public class OrderController {
    private String message;
    public void checkout(String message){
        this.message=message;
    }
}
