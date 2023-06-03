package Controllers;

import com.jfoenix.controls.JFXComboBox;
import dto.Customer;
import dto.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.EmployeeModel;
import model.EmployeeScheduleModel;
import model.PetModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeAddScheduleFormController implements Initializable {
    public AnchorPane dashboardPane;

    @FXML
    private Label lblID;

    @FXML
    private JFXComboBox cmbEmployeeID;


    @FXML
    private Label lblName;


    @FXML
    private DatePicker date;

    @FXML
    private TextField time;

    @FXML
    private TextField worktime;

    @FXML
    private JFXComboBox cmbShift;

    @FXML
    private TextField Ot;




    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        loadShift();
        loadEmployeeID();
        generateNextEmpSchedId();

    }

    private void generateNextEmpSchedId() {
        try {
            String id = EmployeeScheduleModel.getNextSchedId();
            lblID.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void petbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void customerbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void usersbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void employeebtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void suppliesbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/SupplieManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void billingbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/BillingManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void inhousebtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/InhouseManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void logoutbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    private void loadEmployeeID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = EmployeeModel.loadEmployeeID();

            for (String code : codes) {
                obList.add(code);
            }
            cmbEmployeeID.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void loadShift() {
        ObservableList<String> obList = FXCollections.observableArrayList("Day","Night");
        cmbShift.setItems(obList);

    }

    private void fillEmployeeFields(Employee employee) {

        lblName.setText(String.valueOf(employee.getName()));
    }

    public void cmbIDOnAction(ActionEvent event) {
        String ID = (String) cmbEmployeeID.getValue();
        try {
            Employee employee = EmployeeModel.searchById(ID);
            fillEmployeeFields(employee);;

            // txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void savebtnOnAction(ActionEvent event) throws SQLException, IOException {
        String ScheduleID=lblID.getText();
        String EmployeeID= (String) cmbEmployeeID.getValue();
        String Name=lblName.getText();
        LocalDate Date= date.getValue();
        String Time= time.getText();
        String WorkTime=worktime.getText();
        String Shift= (String) cmbShift.getValue();
        String OT=Ot.getText();

        boolean isAdd = false;
        try {
            isAdd = EmployeeScheduleModel.addSchedule(ScheduleID, EmployeeID,Name,Date,Time,WorkTime,Shift,OT);
            if(isAdd) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule added").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not added").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeAddScheduleForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void backbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeScheduleManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }
}
