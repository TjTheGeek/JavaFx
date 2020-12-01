package register;

public class PasswordFactory {

    public Password getTest(String passType){
        if(passType==null){
            return null;
        }else if(passType.equalsIgnoreCase("PWDSAME")){
            //System.out.println("1. Checking same password");
            return new PasswordSame("Pasword do not Match");
        }
        else if(passType.equalsIgnoreCase("PWDLENGTH")){
           // System.out.println("2. checking length");
            return new PasswordLength("Password is not long enough");

        }else if (passType.equalsIgnoreCase("PWDCASE")){
            return new PasswordCase("Please Enter a Capital letter");
        }
        else if(passType.equalsIgnoreCase("PWDDIGIT")){
           // System.out.println("3. checking number");
            return new PasswordDigit("Password does not contain a number");
        } else if(passType.equalsIgnoreCase("PWDCOMPROMISED")){
           // System.out.println("4. Checking Compromised");
            return new PasswordCompromised("Password has been compromised");
        }else return null;
    }
}
