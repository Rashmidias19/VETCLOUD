package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dto.Customer;
import dto.Employee;
import dto.Pet;
import dto.tm.CustomerTM;
import dto.tm.EmployeeTM;
import dto.tm.PetTM;
import javafx.application.Platform;
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
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.BillModel;
import model.CustomerModel;
import model.EmployeeModel;
import model.PetModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PetManageFormController implements Initializable {

    public TableView<PetTM> tblPet;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colCusID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colBreed;

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
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private AnchorPane dashboardPane;


    public void saveBtnOnAction(ActionEvent event) throws IOException {
       // Stage stage=(Stage) dashboardPane.getScene().getWindow();
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetSaveForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    public void updateBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PetUpdateForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();

    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("PetID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCusID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colBreed.setCellValueFactory(new PropertyValueFactory<>("Breed"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }

    private void getAll() {
        try {
            ObservableList<PetTM> obList = FXCollections.observableArrayList();
            List<Pet> petList = PetModel.getAll();

            for (Pet pet : petList) {
                obList.add(new PetTM(
                        pet.getPetID(),
                        pet.getName(),
                        pet.getCustomerID(),
                        pet.getType(),
                        pet.getBreed(),
                        pet.getGender(),
                        pet.getDOB(),
                        pet.getAge(),
                        pet.getAddress(),
                        pet.getContact()
                ));
            }
            tblPet.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    boolean existPet(String id) throws SQLException, ClassNotFoundException {
        return PetModel.exist(id);
    }

    private void initUI() {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);
    }


    public void deleteBtnOnAction(ActionEvent event) throws IOException {
        String id= tblPet.getSelectionModel().getSelectedItem().getPetID();
        try {
            if (!existPet(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such pet associated with the id " + id).show();
            }

            PetModel.delete(id);

            tblPet.getItems().remove(tblPet.getSelectionModel().getSelectedItem());
            tblPet.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the pet " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnReportOnAction(ActionEvent event) {
        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/Pet.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,getCon());
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public Connection getCon(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/VETCLOUD","root","1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
