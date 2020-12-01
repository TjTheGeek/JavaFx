package register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordCase extends Password {
    protected PasswordCase(String message) {
        super(message);
    }
    @Override
    public boolean checkPassword(String pass1, String pass2) {
        char ch;
        boolean capitalFlag = false;
        for (int i = 0; i < pass1.length(); i++) {
            ch = pass1.charAt(i);
            if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            }
        }
        return !capitalFlag;
        /*Pattern digit = Pattern.compile("");
        Matcher hasDigit = digit.matcher(pass1);
        boolean res = hasDigit.find();
        return !res;*/
    }
}

