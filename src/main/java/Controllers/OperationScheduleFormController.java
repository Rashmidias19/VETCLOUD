package Controllers;

import dto.EmployeeSchedule;
import dto.OperationSchedule;
import dto.tm.EmployeeScheduleTM;
import dto.tm.OperationScheduleTM;
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
import model.EmployeeScheduleModel;
import model.OperationScheduleModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class OperationScheduleFormController implements Initializable {

    public TableView<OperationScheduleTM> tblOperation;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colPetID;

    @FXML
    private TableColumn<?, ?> colCusID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colHours;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private AnchorPane dashboardPane;



    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("Operation_ID"));
        colPetID.setCellValueFactory(new PropertyValueFactory<>("Pet_ID"));
        colCusID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("Hours"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));

    }

    private void getAll() {
        try {
            ObservableList<OperationScheduleTM> obList = FXCollections.observableArrayList();
            List<OperationSchedule> operationScheduleList = OperationScheduleModel.getAll();

            for (OperationSchedule operationSchedule : operationScheduleList) {
                obList.add(new OperationScheduleTM(
                        operationSchedule.getOperationID(),
                        operationSchedule.getPetID(),
                        operationSchedule.getCustomerID(),
                        operationSchedule.getDate(),
                        operationSchedule.getTime(),
                        operationSchedule.getDescription(),
                        operationSchedule.getHours(),
                        operationSchedule.getContact()

                ));
            }
            tblOperation.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    public void backbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OperationScheduleManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }
}
