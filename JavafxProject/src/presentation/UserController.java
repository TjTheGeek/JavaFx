package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    BorderPane userBorderPane;

    @FXML
    TextField pinTextField;

    @FXML
    Button btnGoToSuperHomepage;



    @FXML
    protected void setBtnGoToSuperHomepage(ActionEvent event){
        //TODO for Discretionary Access Control


    }


    @FXML
    protected void btnBackAction(ActionEvent event) {
        System.out.println("User Back pressed");
        goToLoginPage();
        closeWindow();
    }




    private void goToLoginPage(){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();

        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        }

    }

    private void closeWindow(){
        Stage stage = (Stage) userBorderPane.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }


}
