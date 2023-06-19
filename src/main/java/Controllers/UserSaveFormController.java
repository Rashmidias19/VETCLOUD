package Controllers;

import dto.User;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserSaveFormController implements Initializable {

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private Label lblID;

    @FXML
    private TextField txtName;


    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextUserId();
    }

    private void generateNextUserId() {
        try {
            String id = UserModel.getNextUserId();
            lblID.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserManageForm.fxml"))));
        stage.setTitle("User Form");
        stage.centerOnScreen();
        stage.show();
    }

    public void savebtnOnAction(ActionEvent event) throws IOException {
        if(txtEmail.getText().matches("^(?:[^.\\s])\\S*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            if (txtPassword.getText().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$")) {
                String UserID = lblID.getText();
                String UserName = txtName.getText();
                String password = txtPassword.getText();
                String email = txtEmail.getText();

                try {
                    boolean isSaved = UserModel.save( new User(UserID,UserName,password,email));
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Please enter a password with minimum 5 characters, with a upper case and lower case letters and atleast one number.").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please enter a valid email").show();

        }

        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserSaveForm.fxml"))));
        stage.setTitle("User Form");
        stage.centerOnScreen();
        stage.show();
    }
}
