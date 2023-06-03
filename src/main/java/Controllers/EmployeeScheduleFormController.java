package Controllers;

import dto.EmployeeSchedule;
import dto.Inhouse;
import dto.tm.EmployeeScheduleTM;
import dto.tm.InhouseTM;
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
import model.InhouseModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeScheduleFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/VETCLOUD";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public TableView<EmployeeScheduleTM> tblEmpSchedule;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colEmpID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colWorkTime;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colShift;

    @FXML
    private TableColumn<?, ?> colOT;

    @FXML
    private AnchorPane dashboardPane;



    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("Schedule_ID"));
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("Employee_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        colWorkTime.setCellValueFactory(new PropertyValueFactory<>("WorkTime"));
        colShift.setCellValueFactory(new PropertyValueFactory<>("Shift"));
        colOT.setCellValueFactory(new PropertyValueFactory<>("OT"));

    }

    private void getAll() {
        try {
            ObservableList<EmployeeScheduleTM> obList = FXCollections.observableArrayList();
            List<EmployeeSchedule> employeeScheduleList = EmployeeScheduleModel.getAll();

            for (EmployeeSchedule employeeSchedule : employeeScheduleList) {
                obList.add(new EmployeeScheduleTM(
                        employeeSchedule.getScheduleID(),
                        employeeSchedule.getEmployeeID(),
                        employeeSchedule.getName(),
                        employeeSchedule.getDate(),
                        employeeSchedule.getTime(),
                        employeeSchedule.getWorkTime(),
                        employeeSchedule.getShift(),
                        employeeSchedule.getOT()
                ));
            }
            tblEmpSchedule.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    public void backbtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeScheduleManagementForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }
}
