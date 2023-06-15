package Controllers;

import dto.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {



    @FXML
    public Button btnRegister;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnLogin;

    @FXML
    private AnchorPane dashboardPane;

    public void loginButtonOnAction(ActionEvent event) throws IOException, SQLException {

//        if ((txtUserName.getText().isEmpty() || txtUserName.getText().isBlank()) || (txtPassword.getText().isEmpty() || txtPassword.getText().isBlank())) {
//            new Alert(Alert.AlertType.ERROR, "Please enter the username or password").show();
//        } else {
//            List<User> userList = null;
//            try {
//                userList = UserModel.getAll();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            boolean isTrue = false;
//            for (int i = 0; i < userList.size(); i++) {
//                if (txtUserName.getText().equals(userList.get(i).getUserName()) && txtPassword.getText().equals(userList.get(i).getPassword())) {
//                    isTrue = true;
//
//                    }
//
//                }if(isTrue){
                Stage stage = (Stage) dashboardPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"))));
                stage.setTitle("VETCLOUD");
                stage.centerOnScreen();
                stage.show();
//            }
//            else {
//                new Alert(Alert.AlertType.ERROR, "Please enter correct username or password").show();
//            }
//
//            }

        }

        @Override
        public void initialize (java.net.URL url, ResourceBundle resourceBundle){

        }

    public void forgotBtnOnAction(ActionEvent event) {
    }
}
