package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import static persistence.StaffDAO.*;


public class LoginController implements Initializable {
    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label messageLabel;


    @FXML
    private Button btnCancel;
    @FXML
    protected void btnCancelAction (ActionEvent event){
        showMessage("");
        closeWindow();

    }

    @FXML
    protected Button  btnLogin;
    @FXML
    protected void btnLoginAction(ActionEvent event){
        System.out.println("Login presssed");
        showMessage("Trying To Login");

        if(usernameTextfield.getText().equals("") || passwordTextField.getText().equals("") ){
            showMessage("Enter valid Username and/or Password");
        }
        else if(validUser(usernameTextfield.getText(),passwordTextField.getText())){//if the user is there
            if(isLockAccount(usernameTextfield.getText())){//if its locked
                showMessage("Account Blocked, Please Contact admin");
                System.out.println("the if is running");
            }
            else{
                showMessage("Login successful");
                String role = getRole(usernameTextfield.getText(), passwordTextField.getText());
                updateLockedData(usernameTextfield.getText(),"UNLOCKED");
                goToHomePage(role);
            }
        }// if the user exists and the password, and if the account is locked

        else if(checkIfExists(usernameTextfield.getText())){
            //if the username exists
            /*if(isLockAccount(usernameTextfield.getText())){
                showMessage("Account Blocked, Please Contact admin");
                System.out.println("the elseif is running");
            }
            else updateAttempts(usernameTextfield.getText()); showMessage("Password is wrong");*/
            showMessage(updateAttempts(usernameTextfield.getText()));
            System.out.println("the elseif is running");
        }
        else{
            showMessage("Invalid Credentials");
        }
        //TODO Alternate flow 4b
    }

    @FXML
    protected Button btnRegister;

    @FXML
    protected void btnRegisterAction(ActionEvent event){

        showMessage("");
        System.out.println("Register Pressed");
        createAccount();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    protected static void transferMessage(String message) {
        //Display the message
        System.out.println(message);
    }

    private void createAccount(){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();

        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        }

    }
    private void goToHomePage(String page){

        try{
            Parent root = FXMLLoader.load(getClass().getResource(page+".fxml"));
            System.out.println("page "+page+".fxml");
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            closeWindow();
        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        }
    }
   /* private boolean validateUser(String name, String pwd){
//validates of teh user is in the DB and the passwords and returns true or false
        boolean result = persistence.StaffDAO.validUser(name, pwd);
        return result;
    }*/
    protected void reset(){
        usernameTextfield.setText("");
        passwordTextField.setText("");
    }

    private void showMessage(String msg){
        messageLabel.setText(msg);
    }

    private void closeWindow(){
        Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
        stage.close();
    }

 /*   public void tranferData(String data){
        System.out.println("Data from "+data);
        showMessage(data);
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login init");

    }
}
