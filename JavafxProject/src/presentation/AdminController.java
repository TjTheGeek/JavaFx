package presentation;

import Model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.StaffDAO;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    AnchorPane adminAncorPane;

    @FXML
    private Label msgLabel;

    @FXML
    TableView<Staff> tableView;

    @FXML
    TableColumn<Staff, String> userNameColumn;

    @FXML
    TableColumn<Staff, String> roleColumn;

    @FXML
    TableColumn<Staff, String> attemptsColumn;

    @FXML
    TableColumn<Staff, Staff.LOCK> isLockedColumn;

    //Not Used Info Coming from Database StaffDAO.getAllStaff();
  /*  public ObservableList<Staff> getStaff(){
        ObservableList<Staff> staff = FXCollections.observableArrayList();
        staff.add(new Staff("admin","admin","LOCKED", "5"));
        staff.add(new Staff("super","super","LOCKED", "3"));
        staff.add(new Staff("user","user","LOCKED", "3"));
        return staff;
    }
*/

    /*
    how to edit TabelColumn User name firstNameCellEdit() Calle4d in Scenebuilder On Edit Commit
    public void userNameCellEdit (TableColumn.CellEditEvent editCell){
        Staff staffSelected = tableView.getSelectionModel().getSelectedItem();
        staffSelected.setUserName(editCell.getNewValue().toString());
    }
    */


    @FXML
    protected void btnBackAction(ActionEvent event){
        System.out.println("Admin Back pressed");
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
        }

    }
    protected void closeWindow(){
        Stage stage = (Stage) adminAncorPane.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msgLabel.setText("");

        userNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("UserName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Role"));
        attemptsColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("LockAttempt"));
        isLockedColumn.setCellValueFactory(new PropertyValueFactory<>("lock"));

        tableView.setItems(StaffDAO.getAllStaff());
        tableView.setEditable(true);
      // userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        isLockedColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Staff.LOCK.values()));
        isLockedColumn.setOnEditCommit(e -> {
            Staff s = e.getRowValue();
            e.getRowValue().setLock(e.getNewValue());
            msgLabel.setText(StaffDAO.updateLockedData(s.getUserName(), e.getNewValue().toString()));
            System.out.println("UserName" + s.getUserName() + "Locked" + e.getNewValue() + " attempts " + s.getRole());
            //refresh TabelView data after update;
            tableView.setItems(StaffDAO.getAllStaff());
        });

        roleColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Super","User"));
        roleColumn.setOnEditCommit(e -> {
            Staff s = e.getRowValue();
            e.getRowValue().setRole(e.getNewValue());
            msgLabel.setText(StaffDAO.updateRoleData(s.getUserName(),e.getNewValue()));
            System.out.println("UserName"+s.getUserName()+"Role"+ e.getNewValue());
            //refresh TabelView data after update;
            tableView.setItems(StaffDAO.getAllStaff());
        });

     userNameColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Deleted"));
        userNameColumn.setOnEditCommit(e -> {
            Staff s = e.getRowValue();
            String userName=s.getUserName();
            System.out.println(userName);
            s.setUserName(e.getNewValue());

            msgLabel.setText(String.valueOf(StaffDAO.deleteUser(userName)));
            System.out.println("UserName "+userName+"Got deleted");
            //refresh TabelView data after update;
            tableView.setItems(StaffDAO.getAllStaff());
        });
    }
}
