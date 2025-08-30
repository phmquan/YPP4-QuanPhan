package vn.ypp4.quanphan.api.util.constant;

import lombok.Getter;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyAutowired;

import java.lang.annotation.Annotation;
import java.util.List;

public class DIAnnotation {
    @Getter
    private static final List<Class<? extends Annotation>> DIList = List.of(
            MyAutowired.class
    );
}
