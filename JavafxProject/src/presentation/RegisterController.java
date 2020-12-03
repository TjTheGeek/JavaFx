package presentation;
import register.Email;
import register.EmailFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.StaffDAO;
import register.Password;
import register.PasswordFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class RegisterController implements Initializable {
    private Stage stage;
    @FXML
    private AnchorPane registerAnchorPane;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private Button btnRegistration;
    @FXML
    TextField userNameTextfield;
    @FXML
    TextField pinTextfield;
    @FXML
    TextField emailTextfield;
    @FXML
    PasswordField passwordTextfield;
    @FXML
    PasswordField confirmPasswordTextfield;
    @FXML
    Label messageLabel;


    @FXML
    protected void btnRegistrationAction(ActionEvent event) {
        System.out.println("Registration Pressed");
        String userName = userNameTextfield.getText();
        String pass1 = passwordTextfield.getText();
        String pass2 = confirmPasswordTextfield.getText();
        String pin = pinTextfield.getText();
        String UserEmail = emailTextfield.getText();
        showMessage("");
        confirmPassword(userName,pass1, pass2, pin, UserEmail);

    }
    private void confirmPassword(String UserName,String pass1, String pass2, String pinEntered, String UserEmail){
        String message;
        // PWDSAME PWDLENGTH PWDDIGIT PWDCOMPROMISED
        //use of factory pattern
        PasswordFactory pf = new PasswordFactory();
        EmailFactory ef=new EmailFactory();
        Password pwd1 = pf.getTest("PWDSAME");
        Password pwd2 = pf.getTest("PWDLENGTH");
        Password pwd3 = pf.getTest("PWDCOMPROMISED");
        Password pwd4 = pf.getTest("PWDDIGIT");
        Password pwd5=pf.getTest("PWDCASE");
        //from the email factory
      Email em1=ef.geEmail("EMAILC");
      boolean email=true;
        if(em1.checkEmailChar(emailTextfield.getText())){
            message= em1.getMessage();
            showMessage(message);
            System.out.println("email failed");
            email=false;
        }
        //pin check
        boolean pin=true;
        if(pinEntered.equals("")) {
            pin=false;
            showMessage("Enter Pin");
        }

        //password check
        List<Password> list = new ArrayList<Password>();
        list.add(pwd3);
        list.add(pwd4);
        list.add(pwd1);
        list.add(pwd5);
        list.add(pwd2);
        boolean pass=true;
        for(Password p :list){
            if(p.checkPassword(pass1,pass2)){
                message =p.getMessage();
                System.out.println(" message "+message);
                showMessage(message);
                pass=false;
            }
        }

        if (pass&&email){//if email and password are true try add to database
            if(StaffDAO.addUser(UserName,pass1,pinEntered,UserEmail)==1){
                returnToLogin();
            }
            else if(!pin){
                showMessage("Enter Pin");
            }
            else showMessage("Username Already Used");
        }
    }
/**/
    @FXML
    protected Button btnCancelRegistration;

    public void btntnCancelRegistrationAction(ActionEvent event) {
        returnToLogin();
        closeWindow();
    }
    @FXML
    protected   Button btnReset;
    public void clearPassword() {
        passwordTextfield.setText("");
        confirmPasswordTextfield.setText("");
    }

    private void returnToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.transferMessage("Hello World ?");
            //Show Login in new window
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.show();

        } catch (Exception ex) {
        }
    }
    public void showMessage(String msg) {
        messageLabel.setText(msg);
    }

    private void closeWindow() {
        Stage stage = (Stage) registerAnchorPane.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Init Resister ");

    }
}