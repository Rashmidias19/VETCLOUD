package Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.PetModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PetDeleteForm1Controller implements Initializable {

    @FXML
    private JFXComboBox cmbID;

    @FXML
    private AnchorPane dashboardPane;

    public void deletebtnOnAction(ActionEvent event) throws IOException {
            String id = (String) cmbID.getValue();

            try {
                boolean isDeleted = PetModel.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            }
            Stage stage = (Stage) dashboardPane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetDeleteForm1.fxml"))));
            stage.setTitle("VETCLOUD");
            stage.centerOnScreen();
            stage.show();

    }

    private void loadPetsID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = PetModel.loadPetID();

            for (String code : codes) {
                obList.add(code);
            }
            cmbID.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPetsID();
    }

    public void backbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }
}
