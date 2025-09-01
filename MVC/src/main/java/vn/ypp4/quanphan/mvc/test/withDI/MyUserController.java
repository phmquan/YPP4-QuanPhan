package vn.ypp4.quanphan.mvc.test.withDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyController;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyRequestMapping;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyRequestParam;

@MyController
@Controller
@MyRequestMapping("/myUser")
public class MyUserController {
    @MyAutowired
    @Autowired
    private MyUserService myUserService;

    @MyRequestMapping(value = "")
    public String showUser(@MyRequestParam("id") int id) {
        return myUserService.getUserById(id);
    }
}
