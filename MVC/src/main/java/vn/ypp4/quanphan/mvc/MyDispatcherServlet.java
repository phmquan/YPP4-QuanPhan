package vn.ypp4.quanphan.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ypp4.quanphan.mvc.customDI.container.MyApplicationContext;
import vn.ypp4.quanphan.mvc.handlerAdapter.MyHandlerAdapter;
import vn.ypp4.quanphan.mvc.view.ModelAndView;
import vn.ypp4.quanphan.mvc.view.MyViewResolver;
import java.io.IOException;
import java.util.Collections;

public class MyDispatcherServlet {
    private MyHandlerMapping handlerMapping;
    private MyHandlerAdapter handlerAdapter;
    private MyViewResolver viewResolver;

    public void init(String suffix, String prefix, String basePackage) {
        try {
            MyApplicationContext myApplicationContext = new MyApplicationContext(basePackage);
            handlerMapping = new vn.ypp4.quanphan.mvc.MyHandlerMapping(basePackage, myApplicationContext);
            handlerAdapter = new MyHandlerAdapter();
            viewResolver = new MyViewResolver(prefix, suffix);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String httpMethod = req.getMethod();

        MyHandlerMethod handlerMethod = handlerMapping.getHandler(uri, httpMethod);
        if (handlerMethod == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No handler found for " + uri);
            return;
        }

        try {
            ModelAndView result = handlerAdapter.handle(
                    handlerMethod.getController(),
                    handlerMethod.getMethod(),
                    req,
                    Collections.emptyMap());

            if (result != null) {
                System.out.println("Result: viewName=" + result.getViewName() + ", modelEmpty=" + result.getModel().isEmpty());
                if (result.getViewName() != null && result.getModel().isEmpty()){
                    resp.setContentType("text/plain;charset=UTF-8");
                    resp.getWriter().write(result.getViewName());
                } else {
                    viewResolver.render(result, req, resp);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }


}
