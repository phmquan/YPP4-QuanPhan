package vn.ypp4.quanphan.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class MvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> myDispatcherServletRegistration() {
        HttpServlet servlet = new HttpServlet() {
            private MyDispatcherServlet myDispatcherServlet;

            @Override
            public void init() throws ServletException {
                super.init();
                myDispatcherServlet = new MyDispatcherServlet();
                // Initialize with template configuration
                myDispatcherServlet.init(".html", "/templates/", "vn.ypp4.quanphan.mvc.test");
            }

            @Override
            protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                myDispatcherServlet.service(req, resp);
            }
        };

        ServletRegistrationBean<HttpServlet> registration = new ServletRegistrationBean<>(servlet, "/*");
        registration.setLoadOnStartup(1);
        return registration;
    }
}
