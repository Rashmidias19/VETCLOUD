package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CustomerModel;
import model.EmployeeModel;
import model.UserModel;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeRegisterFormController implements Initializable {
    public AnchorPane dashboardPane;
    private static final String URL = "jdbc:mysql://localhost:3306/VETCLOUD";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }


    @FXML
    private Label lblID;

    @FXML
    private TextField txtName;


    @FXML
    private TextField txtAge;


    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker date;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox cmbGender;

    @FXML
    private ComboBox cmbUserID;

    @FXML
    private TextField txtImage;

    private FileChooser fileChooser;

    private File file;

    private Desktop desktop=Desktop.getDesktop();

    private FileInputStream inp;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        generateNextEmpId();
        loadGender();
        loadUserID();

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
        ObservableList<String> obList = FXCollections.observableArrayList("Male", "Female");
        cmbGender.setItems(obList);

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

    private void generateNextEmpId() {
        try {
            String id = EmployeeModel.getNextEmpId();
            lblID.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void savebtnOnAction(ActionEvent event) throws SQLException, IOException {

        if (txtEmail.getText().matches("^(?:[^.\\s])\\S*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            if (Integer.parseInt(txtAge.getText()) > 19 && Integer.parseInt(txtAge.getText()) < 70) {

                String EmployeeID = lblID.getText();
                String Name = txtName.getText();
                String UserID = (String) cmbUserID.getValue();
                LocalDate DOB = date.getValue();
                String NIC = txtNIC.getText();
                int Age = Integer.parseInt(txtAge.getText());
                String Gender = (String) cmbGender.getValue();
                String address = txtAddress.getText();
                String Salary = txtSalary.getText();
                String contact = txtContact.getText();
                String email = txtEmail.getText();


                try (Connection con = DriverManager.getConnection(URL, props)) {
                    String sql = "INSERT INTO Employee(EmployeeID,Name,UserID,DOB,NIC,Age,Gender,address,Salary,contact,email,picture)" +
                            "VALUES(?, ?, ?, ?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setString(1, EmployeeID);
                    pstm.setString(2, Name);
                    pstm.setString(3, UserID);
                    pstm.setDate(4, java.sql.Date.valueOf(DOB));
                    pstm.setString(5, NIC);
                    pstm.setInt(6, Age);
                    pstm.setString(7, Gender);
                    pstm.setString(8, address);
                    pstm.setString(9, Salary);
                    pstm.setString(10, contact);
                    pstm.setString(11, email);
                    inp=new FileInputStream(file);
                    pstm.setBinaryStream(12,(InputStream) inp,(int)file.length());

                    int affectedRows = pstm.executeUpdate();
                    if (affectedRows > 0) {
                        new Alert(Alert.AlertType.CONFIRMATION,
                                "Employee added :)")
                                .show();
                    }

                }}else{
                    new Alert(Alert.AlertType.ERROR, "Please enter a valid age between 20-70").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Please enter a valid email").show();

            }
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeRegisterForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();

        }

    public void btnAddOnAction(ActionEvent event) throws FileNotFoundException {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif","*.")
        );
        fileChooser.setTitle("Choose File");
        file = fileChooser.showOpenDialog(null);
        inp=new FileInputStream(file);
        if (file != null){
            try {
                desktop.open(file);
                txtImage.setText(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

