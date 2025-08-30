package vn.ypp4.quanphan.customMVC.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyController;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyRequestMapping;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyRequestParam;
import vn.ypp4.quanphan.customMVC.view.ModelAndView;
import vn.ypp4.quanphan.customMVC.constant.HttpMethod;

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

    @MyRequestMapping(value= "/detail", method= HttpMethod.POST)
    public String detail(@MyRequestParam("id") int id) {
        return "User detail for id=" + id;
    }


    @MyRequestMapping("/request")
    public String requestInfo(HttpServletRequest request) {
        return "Method=" + request.getMethod() + ", URI=" + request.getRequestURI();
    }

    @MyRequestMapping("/testMVC")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("message", "Hello from HomeController!");
        mv.addObject("time", new java.util.Date());
        return mv;
    }

}
