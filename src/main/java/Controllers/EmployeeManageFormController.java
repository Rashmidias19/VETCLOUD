package Controllers;

import com.jfoenix.controls.JFXButton;
import dto.Employee;
import dto.tm.EmployeeTM;
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
import model.CustomerModel;
import model.EmployeeModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeManageFormController implements Initializable {
    public TableView<EmployeeTM> tblEmployee;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

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
        String id= tblEmployee.getSelectionModel().getSelectedItem().getEmployeeID();
        try {
            if (!existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
            }

            EmployeeModel.delete(id);

            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            tblEmployee.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Failed to delete the employee " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnScheduleOnAction(ActionEvent event) {
    }

    boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return EmployeeModel.exist(id);
    }

    private void initUI() {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

    }

    private void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<Employee> employeeList = EmployeeModel.getAll();

            for (Employee employee : employeeList) {
                obList.add(new EmployeeTM(
                        employee.getEmployeeID(),
                        employee.getName(),
                        employee.getUserID(),
                        employee.getDOB(),
                        employee.getNIC(),
                        employee.getAge(),
                        employee.getGender(),
                        employee.getAddress(),
                        employee.getSalary(),
                        employee.getContact(),
                        employee.getEmail()
                ));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
}
