package register;

public class PasswordSame extends Password {

    protected PasswordSame(String message) {
        super(message);
    }
    @Override
    public boolean checkPassword(String pass1, String pass2) {
        return !pass1.equals(pass2);
    }
}
