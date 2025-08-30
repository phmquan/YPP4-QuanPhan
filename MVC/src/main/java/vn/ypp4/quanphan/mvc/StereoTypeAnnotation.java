package vn.ypp4.quanphan.api.util.constant;

import lombok.Getter;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyComponent;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyController;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyRepository;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyService;

import java.lang.annotation.Annotation;
import java.util.List;

public class StereoTypeAnnotation {
    @Getter
    private static final List<Class<? extends Annotation>> stereotypeList = List.of(
            MyController.class,
            MyService.class,
            MyRepository.class,
            MyComponent.class
    );
}
