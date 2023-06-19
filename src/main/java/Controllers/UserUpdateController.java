package Controllers;

import com.jfoenix.controls.JFXComboBox;
import dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserUpdateController implements Initializable {

    public AnchorPane dashboardPane;

    @FXML
    private Label lblID;

    @FXML
    private TextField txtName;

    @FXML
    private JFXComboBox cmbUserID;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtEmail;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        loadUserID();
    }

    private void loadUserID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = UserModel.loadUserID();

            for (String code : codes) {
                obList.add(code);
            }
            cmbUserID.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    public void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void btnUpdateOnAction(ActionEvent event) throws IOException {
        String UserID=lblID.getText();
        String UserName= txtName.getText();
        String Password=txtPassword.getText();
        String email=txtEmail.getText();


        try {
            boolean isUpdated = UserModel.update( new User(UserID,UserName,Password,email));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }

        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserUpdate.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void searchbtnOnAction(ActionEvent event) {
        String UserID= (String) cmbUserID.getValue();
        try {
            User user = UserModel.searchById(UserID);
            fillUserFields(user);

            // txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillUserFields(User user) {
        lblID.setText(user.getUserID());
        txtName.setText(user.getUserName());
        txtPassword.setText(user.getPassword());
        txtEmail.setText(user.getEmail());
    }
}
