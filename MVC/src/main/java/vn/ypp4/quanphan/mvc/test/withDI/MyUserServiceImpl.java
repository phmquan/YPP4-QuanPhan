package vn.ypp4.quanphan.customMVC.test.withDI;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyService;

@MyService
@Service
public class MyUserServiceImpl implements MyUserService {
    @Override
    public String getUserById(int id) {
        return "Get user with id: "+id;
    }
}
