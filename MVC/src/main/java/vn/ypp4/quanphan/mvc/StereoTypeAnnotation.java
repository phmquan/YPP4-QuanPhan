package vn.ypp4.quanphan.mvc;

import lombok.Getter;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyComponent;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyController;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyRepository;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyService;

import java.lang.annotation.Annotation;
import java.util.List;

public class StereoTypeAnnotation {
    @Getter
    private static final List<Class<? extends Annotation>> stereotypeList = List.of(
            MyController.class,
            MyService.class,
            MyRepository.class,
            MyComponent.class);
}
