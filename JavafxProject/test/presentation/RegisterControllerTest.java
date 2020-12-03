package presentation;

import org.testng.Assert;
import org.testng.annotations.Test;
import persistence.StaffDAO;

import static org.testng.Assert.*;

public class RegisterControllerTest {
    @Test
    public void confirmPassword(){


    }

    @Test
    public void testConfirmPasswordForTest() {
        String  pass1, pass2,pin, email,username;
        pass1="Lolage123";
        pass2="Lolage123";
        pin="123456";
        email="Tjief@gmail.com";
        username="asdsd12";
        RegisterController rc=new RegisterController();

        System.out.println();
        Assert.assertEquals(true,rc.confirmPasswordForTest(username,pass1,pass2,pin,email));
    }
}