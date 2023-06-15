package Controllers;

import dto.Customer;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerSaveFormController implements Initializable {

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private TextField txtAddress;

    @FXML
    private Label lblID;

    @FXML
    private TextField txtName;


    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtGender;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox cmbTitle;

    @FXML
    private ComboBox cmbGender;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextCustomerId();
        loadGender();
        loadTitles();
    }

    private void loadGender() {
        ObservableList<String> obList = FXCollections.observableArrayList("Male","Female","Other");
        cmbGender.setItems(obList);

    }

    private void loadTitles() {
        ObservableList<String> obList = FXCollections.observableArrayList("Miss","Mr","Mrs");
        cmbTitle.setItems(obList);

    }



    private void generateNextCustomerId() {
        try {
            String id = CustomerModel.getNextCustomerId();
            lblID.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    public void savebtnOnAction(ActionEvent event) throws SQLException, IOException {

        if(txtEmail.getText().matches("^(?:[^.\\s])\\S*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            if(Integer.parseInt(txtAge.getText())>17 && Integer.parseInt(txtAge.getText())<100 ){
                String CustomerID=lblID.getText();
                String CustTitle= (String) cmbTitle.getValue();
                String CustName=txtName.getText();
                String NIC=txtNIC.getText();
                String DOB= String.valueOf(date.getValue());
                int age=Integer.parseInt(txtAge.getText());
                String Gender=(String)cmbGender.getValue();
                String contact=txtContact.getText();
                String email = txtEmail.getText();
                String address=txtAddress.getText();

                Customer customer = new Customer(CustomerID,CustTitle,CustName,NIC,DOB,age,Gender,contact,email,address);

                try {
                    boolean isSaved = CustomerModel.save(customer);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer saved!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
                }


            }else {
                new Alert(Alert.AlertType.ERROR, "Please enter a valid age between 18-100").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please enter a valid email").show();

        }
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerSaveForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }
}
