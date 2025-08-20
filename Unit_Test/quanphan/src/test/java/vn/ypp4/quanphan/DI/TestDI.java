package vn.ypp4.quanphan.DI;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.controller.BoardController;
import vn.ypp4.quanphan.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customDI.annotation.MyComponent;
import vn.ypp4.quanphan.customDI.annotation.MyQualifier;
import vn.ypp4.quanphan.customDI.context.MyApplicationContext;
import vn.ypp4.quanphan.customDI.test.OrderController;
import vn.ypp4.quanphan.dto.board.BoardResponseDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MyComponent
@SpringBootTest

public class TestDI {
    final MyApplicationContext context= new MyApplicationContext("vn.ypp4.quanphan.customDI");
    @Test
    void testDIWithoutAutowired() {

        OrderController orderController= context.getBean(OrderController.class);
        assertEquals("hehe", orderController.checkout("hehe"));
    }

}
