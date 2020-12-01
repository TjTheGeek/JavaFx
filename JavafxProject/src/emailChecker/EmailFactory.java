package emailChecker;

import register.Password;

public class EmailFactory {
    public Email geEmail(String emailChecker){
        if(emailChecker==null){
            return null;
        }
        else if(emailChecker.equalsIgnoreCase("EMAILC")){
            return new CheckEmail("Invalid Email");
        }
        else return null;
    }

}
