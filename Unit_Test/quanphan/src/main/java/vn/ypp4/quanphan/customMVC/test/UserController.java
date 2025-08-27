package vn.ypp4.quanphan.customMVC.test;

import jakarta.servlet.http.HttpServletRequest;
import vn.ypp4.quanphan.customDI.annotation.MyController;
import vn.ypp4.quanphan.customDI.annotation.MyPathVariable;
import vn.ypp4.quanphan.customDI.annotation.MyRequestMapping;
import vn.ypp4.quanphan.customDI.annotation.MyRequestParam;

@MyController
@MyRequestMapping("/user")
public class UserController {
    @MyRequestMapping
    public String defaultReturn(){
        return "default user";
    }
    @MyRequestMapping("/hello")
    public String hello() {
        return "Hello from UserController";
    }

    @MyRequestMapping("/detail")
    public String detail(@MyRequestParam("id") int id) {
        return "User detail for id=" + id;
    }

    @MyRequestMapping("/path")
    public String pathExample(@MyPathVariable("userId") int userId) {
        return "User with path variable id=" + userId;
    }

    @MyRequestMapping("/request")
    public String requestInfo(HttpServletRequest request) {
        return "Method=" + request.getMethod() + ", URI=" + request.getRequestURI();
    }
}
