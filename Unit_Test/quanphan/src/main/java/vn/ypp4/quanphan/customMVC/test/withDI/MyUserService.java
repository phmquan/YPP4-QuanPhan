package vn.ypp4.quanphan.customMVC.test.withDI;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.customDI.annotation.MyService;

@MyService
@Service
public interface MyUserService {
    String getUserById(int id);
}
