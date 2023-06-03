package Controllers;

import dto.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    public AnchorPane dashboardPane;
    @FXML
    private Label lblPet;

    @FXML
    private Label lblEmployee;

    @FXML
    private Label lblCustomers;

    @FXML
    private Label lblUsers;

    @FXML
    private Label lblInhouse;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblBill;

    public void btnReportOnAction(ActionEvent event) {
    }

    public void btnAboutOnAction(ActionEvent event) {
    }

    public void btnServiceOnAction(ActionEvent event) {
    }

    public void btnLogoutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void btnManageOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadValues();
    }

    private void loadValues() throws SQLException {
        try{
            List<User> userList= UserModel.getAll();
            List<Customer> customerList= CustomerModel.getAll();
            List<Pet> petList= PetModel.getAll();
            List<Employee> employeeList= EmployeeModel.getAll();
            List<Item> itemList= ItemModel.getAll();
            //List<Bill> billList=BillModel.getAll();
            List<Inhouse> inhouseList=InhouseModel.getAll();
            lblUsers.setText(String.valueOf(userList.size()));
            lblCustomers.setText(String.valueOf(customerList.size()));
            lblPet.setText(String.valueOf(petList.size()));
            lblEmployee.setText(String.valueOf(employeeList.size()));
            lblItem.setText(String.valueOf(itemList.size()));
            lblInhouse.setText(String.valueOf(inhouseList.size()));
           // lblBill.setText(String.valueOf(billList.size()));


        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "something happend!").show();
        }
    }
}
