package vn.ypp4.quanphan.customMVC.test.withDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ypp4.quanphan.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customDI.annotation.MyController;
import vn.ypp4.quanphan.customDI.annotation.MyRequestMapping;
import vn.ypp4.quanphan.customDI.annotation.MyRequestParam;

@MyController
@Controller
@MyRequestMapping("/myUser")
public class MyUserController {
    @MyAutowired
    @Autowired
    private MyUserService myUserService;

    @MyRequestMapping(value="")
    public String showUser(@MyRequestParam("id") int id) {
        return myUserService.getUserById(id);
    }
}
