package Controllers;

import dto.OperationSchedule;
import dto.VaccinationSchedule;
import dto.tm.OperationScheduleTM;
import dto.tm.VaccinationTM;
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
import model.OperationScheduleModel;
import model.VaccinationScheduleModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class VaccineScheduleFormController implements Initializable {

    public TableView<VaccinationTM> tblVaccination;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colPetID;

    @FXML
    private TableColumn<?, ?> colCustID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colDescription;

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
        colID.setCellValueFactory(new PropertyValueFactory<>("Vaccination_ID"));
        colPetID.setCellValueFactory(new PropertyValueFactory<>("Pet_ID"));
        colCustID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));

    }

    private void getAll() {
        try {
            ObservableList<VaccinationTM> obList = FXCollections.observableArrayList();
            List<VaccinationSchedule> vaccinationScheduleList = VaccinationScheduleModel.getAll();

            for (VaccinationSchedule vaccinationSchedule : vaccinationScheduleList) {
                obList.add(new VaccinationTM(
                        vaccinationSchedule.getVaccinationID(),
                        vaccinationSchedule.getPetID(),
                        vaccinationSchedule.getCustomerID(),
                        vaccinationSchedule.getDate(),
                        vaccinationSchedule.getTime(),
                        vaccinationSchedule.getDescription(),
                        vaccinationSchedule.getContact()

                ));
            }
            tblVaccination.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    public void backbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ScheduleManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }
}
