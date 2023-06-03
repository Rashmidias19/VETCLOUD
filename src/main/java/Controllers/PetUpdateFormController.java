package Controllers;

import com.jfoenix.controls.JFXComboBox;
import dto.Pet;
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
import model.PetModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PetUpdateFormController implements Initializable {

    public AnchorPane dashboardPane;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtBreed;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker date;

    @FXML
    private JFXComboBox cmbPetID;

    @FXML
    private JFXComboBox cmbSpecies;

    @FXML
    private JFXComboBox cmbGender;

    @FXML
    private JFXComboBox cmbCustomerID;

    @FXML
    private Circle circle;



    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        loadPetID();

    }


    private void loadPetID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = PetModel.loadPetID();

            for (String code : codes) {
                obList.add(code);
            }
            cmbPetID.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadCustID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = CustomerModel.loadCustomerID();

            for (String code : codes) {
                obList.add(code);
            }
            cmbCustomerID.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }
    private void loadGender() {
        ObservableList<String> obList = FXCollections.observableArrayList("Male","Female");
        cmbGender.setItems(obList);

    }
    private void loadTypes() {
        ObservableList<String> obList = FXCollections.observableArrayList("Dog","Cat","Bird","Other");
        cmbSpecies.setItems(obList);

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }



    public void cmbPetIDOnAction(ActionEvent event) {
    }

    public void searchbtnOnAction(ActionEvent event) {
        String PetID= (String) cmbPetID.getValue();
        try {
            Pet pet = PetModel.searchById(PetID);
            fillPetFields(pet);
            loadCustID();
            loadTypes();
            loadGender();
            loadImage(pet);

            // txtQty.requestFocus();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadImage(Pet pet) throws FileNotFoundException, SQLException {
        InputStream is=null;
        if(pet.getPicture()==null){
            is=new FileInputStream("F:\\OOP Final\\petClinic\\src\\main\\resources\\img\\images.png");
        }else{
            is=pet.getPicture().getBinaryStream();
        }
        Image image=new Image(is);
        circle.setFill(new ImagePattern(image));
    }

    private void fillPetFields(Pet pet) {
        txtID.setText(pet.getPetID());
        txtName.setText(pet.getName());
        cmbCustomerID.setPromptText(pet.getCustomerID());
        cmbSpecies.setPromptText(pet.getType());
        txtBreed.setText(pet.getBreed());
        cmbGender.setPromptText(pet.getGender());
        date.setPromptText(pet.getDOB());
        txtAge.setText(String.valueOf(pet.getAge()));
        txtAddress.setText(pet.getAddress());
        txtContact.setText(pet.getContact());

    }


    public void btnUpdateOnAction(ActionEvent event) throws IOException {
        String PetID=txtID.getText();
        String Name=txtName.getText();
        String CustomerID= (String) cmbCustomerID.getValue();
        String Type= (String) cmbSpecies.getValue();
        String Breed=txtBreed.getText();
        String Gender= (String) cmbGender.getValue();
        LocalDate DOB= LocalDate.parse(String.valueOf(date.getValue()));
        int age=Integer.parseInt(txtAge.getText());
        String address=txtAddress.getText();
        String contact=txtContact.getText();

        Pet pet=new Pet(PetID,Name,CustomerID,Type,Breed,Gender,DOB,age,address,contact);

        try {
            boolean isSaved = PetModel.update(pet);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Pet Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }

        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetUpdateForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();

    }

    public void deletebtnOnAction(ActionEvent event) {
    }
}
