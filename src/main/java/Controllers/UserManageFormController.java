package Controllers;

import com.jfoenix.controls.JFXButton;
import dto.Pet;
import dto.User;
import dto.tm.PetTM;
import dto.tm.UserTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.PetModel;
import model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserManageFormController implements Initializable {

    @FXML
    private AnchorPane dashboardPane;

    public TableView<UserTM> tblUser;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colEmail;

    public void saveBtnOnAction(ActionEvent event) {
    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void updateBtnOnAction(ActionEvent event) {
    }

    public void deleteBtnOnAction(ActionEvent event) {
        String id= tblUser.getSelectionModel().getSelectedItem().getUserID();
        try {
            if (!existUser(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such user associated with the id " + id).show();
            }

            UserModel.delete(id);

            tblUser.getItems().remove(tblUser.getSelectionModel().getSelectedItem());
            tblUser.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the user " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);

    }

    private boolean existUser(String id) throws SQLException {
        return UserModel.exist(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void getAll() {
        try {
            ObservableList<UserTM> obList = FXCollections.observableArrayList();
            List<User> userList = UserModel.getAll();

            for (User dto : userList) {
                obList.add(new UserTM(
                        dto.getUserID(),
                        dto.getUserName(),
                        dto.getPassword(),
                        dto.getEmail()
                ));
            }
            tblUser.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    public void btnReportOnAction(ActionEvent event) {
    }
}
