package persistence;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StaffDAOTest {



    @Test(priority = 1)
    public void testValidateUserTrue() {
        String name="admin";
        System.out.println("Check valid user");
        Assert.assertEquals(true,StaffDAO.checkIfExists(name));
    }
    @Test(priority = 3)
    public void testValidateUserFalse() {
        String name="hacker";
        System.out.println("invalid User");
        Assert.assertEquals(false,StaffDAO.checkIfExists(name));
    }
    @Test(priority =2 )
    public void testValidateUser() {
        System.out.println("Check sql injection using OR 1=1");
        Assert.assertEquals(false,StaffDAO.checkIfExists("hacker\' or\"\"=\'"));

    }
}