package vn.ypp4.quanphan.customMVC.customDI.annotation;

import vn.ypp4.quanphan.customMVC.constant.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface MyRequestMapping {
    public String value() default "";
    public HttpMethod method() default HttpMethod.GET;
}
