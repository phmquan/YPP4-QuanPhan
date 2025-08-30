package vn.ypp4.quanphan.mvc.test.withDI;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyService;

@MyService
@Service
public interface MyUserService {
    String getUserById(int id);
}
