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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.CustomerModel;
import model.EmployeeModel;
import model.UserModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeUpdateFormController implements Initializable {


    public AnchorPane dashboardPane;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private JFXComboBox cmbUserID;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSalary;

    @FXML
    private DatePicker date;

    @FXML
    private JFXComboBox cmbGender;

    @FXML
    private JFXComboBox cmbEmployeeID;

    @FXML
    private Circle circle;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        loadEmployeeID();
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

    private void loadUserID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = UserModel.loadUserID();

            for (String code : codes) {
                obList.add(code);
            }
            cmbUserID.setItems(obList);
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

    private void loadGender() {
        ObservableList<String> obList = FXCollections.observableArrayList("Male","Female");
        cmbGender.setItems(obList);

    }


    public void savebtnOnAction(ActionEvent event) {

    }

    public void searchbtnOnAction(ActionEvent event) {
        String EmployeeID= (String) cmbEmployeeID.getValue();
        try {
            Employee employee = EmployeeModel.searchById(EmployeeID);
            fillEmployeeFields(employee);
            loadUserID();
            loadGender();
            loadImage(employee);

            // txtQty.requestFocus();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadImage(Employee employee) throws FileNotFoundException, SQLException {
        InputStream is=null;
        if(employee.getPicture()==null){
            is=new FileInputStream("F:\\OOP Final\\petClinic\\src\\main\\resources\\img\\images.png");
        }else{
            is=employee.getPicture().getBinaryStream();
        }
        Image image=new Image(is);
        circle.setFill(new ImagePattern(image));
    }

    private void fillEmployeeFields(Employee employee) {
        txtID.setText(employee.getEmployeeID());
        txtName.setText(employee.getName());
        cmbUserID.setPromptText(employee.getUserID());
        date.setPromptText(employee.getDOB());
        txtNIC.setText(employee.getNIC());
        txtAge.setText(String.valueOf(employee.getAge()));
        cmbGender.setPromptText(employee.getGender());
        txtAddress.setText(employee.getAddress());
        txtSalary.setText(employee.getSalary());
        txtContact.setText(employee.getContact());
        txtEmail.setText(employee.getEmail());

    }

    public void updatebtnOnAction(ActionEvent event) throws SQLException, IOException {
        String EmployeeID=txtID.getText();
        String Name=txtName.getText();
        String UserID= (String) cmbUserID.getValue();
        String DOB= String.valueOf(date.getValue());
        String NIC=txtNIC.getText();
        int Age=Integer.parseInt(txtAge.getText());
        String gender= (String) cmbGender.getValue();
        String address=txtAddress.getText();
        String salary=txtSalary.getText();
        String contact=txtContact.getText();
        String email=txtEmail.getText();

        try {
            boolean isUpdate = EmployeeModel.update(new Employee(EmployeeID,Name,UserID,DOB,NIC,Age,gender,address,salary,contact,email));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeUpdateForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();

    }
}
