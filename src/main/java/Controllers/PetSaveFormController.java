package Controllers;

import dto.Pet;
import dto.tm.PetTM;
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
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CustomerModel;
import model.PetModel;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class PetSaveFormController implements Initializable {
    public TableView<PetTM> tblPet;


    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private Label lblID;

    @FXML
    private TextField txtName;


    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtImage;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtBreed;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox cmbSpecies;

    @FXML
    private ComboBox cmbGender;

    @FXML
    private Label lblImg;

    @FXML
    private ComboBox cmbCustomerID;

    private FileChooser fileChooser;

    private File file;

    private Desktop desktop=Desktop.getDesktop();

    private FileInputStream inp;

    private FileInputStream fsp;

    private Circle circle;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        generateNextPetId();
        loadGender();
        loadSpecies();
        loadCustID();
    }

    private void loadSpecies() {
        ObservableList<String> obList = FXCollections.observableArrayList("Dog","Cat","Bird","Other");
        cmbSpecies.setItems(obList);
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

    private void generateNextPetId() {
        try {
            String id = PetModel.getNextPetId();
            lblID.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private String getLastPetId() {
        List<PetTM> tempPetList = new ArrayList<>(tblPet.getItems());
        Collections.sort(tempPetList);
        return tempPetList.get(tempPetList.size() - 1).getPetID();
    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void savebtnOnAction(ActionEvent event) throws IOException, SQLException {
        String PetID=lblID.getText();
        String Name=txtName.getText();
        String CustomerID= (String) cmbCustomerID.getValue();
        String Type= (String) cmbSpecies.getValue();
        String Breed=txtBreed.getText();
        String Gender= (String) cmbGender.getValue();
        String DOB= String.valueOf(LocalDate.parse(String.valueOf(date.getValue())));
        int age=Integer.parseInt(txtAge.getText());
        String address=txtAddress.getText();
        String contact=txtContact.getText();

        try {
            boolean isSaved = PetModel.save(new Pet(PetID,Name,CustomerID,Type,Breed,Gender,DOB,age,address,contact),inp,file);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Pet Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }

        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetRegisterForm.fxml"))));
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
