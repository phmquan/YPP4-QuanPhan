import controller.UserController;
import customDI.MyApplicationContext;
import customDI.annotation.MyAutowired;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        @MyAutowired
        private UserController userController;
        MyApplicationContext myApplicationContext=new MyApplicationContext("base.package");
        userController.getUser(1);
    }
}