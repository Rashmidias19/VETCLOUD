package Controllers;

import com.jfoenix.controls.JFXComboBox;
import dto.Customer;
import dto.Item;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ItemModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SupplieUpdateFormCotroller implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/VETCLOUD";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public AnchorPane dashboardPane;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private JFXComboBox cmbType;

    @FXML
    private TextField txtSuppName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtPrice;

    @FXML
    private DatePicker ManDate;

    @FXML
    private DatePicker ExpDate;

    @FXML
    private JFXComboBox cmbItemID;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        loadItemID();
    }

    private void loadItemID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.loadItemID();

            for (String code : codes) {
                obList.add(code);
            }
            cmbItemID.setItems(obList);
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
    public void searchbtnOnAction(ActionEvent event) {
        String ItemID= (String) cmbItemID.getValue();
        try {
            Item item = ItemModel.searchById(ItemID);
            fillItemFields(item);
            loadTypes();
            // txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadTypes() {
        ObservableList<String> obList = FXCollections.observableArrayList("Vitamin","Vaccine","Medicines");
        cmbType.setItems(obList);
    }

    private void fillItemFields(Item item) {
        txtID.setText(item.getItemID());
        txtName.setText(item.getName());
        ManDate.setPromptText(item.getMan_Date());
        ExpDate.setPromptText(item.getExp_Date());
        txtSuppName.setText(item.getSupplier_name());
        cmbType.setPromptText(item.getType());
        txtContact.setText(item.getSupplier_contact());
        txtDescription.setText(item.getDescription());
        txtQuantity.setText(item.getQuantity());
        txtPrice.setText(String.valueOf(item.getPrice()));
    }

    public void updatebtnOnAction(ActionEvent event) throws SQLException, IOException {
        String ItemID=txtID.getText();
        String Name=txtName.getText();
        String Man_Date= String.valueOf(ManDate.getValue());
        String Exp_Date= String.valueOf(ExpDate.getValue());
        String Supplier_name=txtSuppName.getText();
        String Type= (String) cmbType.getValue();
        String Supplier_contact=txtContact.getText();
        String Description=txtDescription.getText();
        String Quantity=txtQuantity.getText();
        Double Price= Double.valueOf(txtPrice.getText());


        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Item SET Name = ?,  Man_Date = ?, Exp_Date = ?, Supplier_name = ?, Type = ?, Supplier_contact = ?, Description = ?, Quantity = ?, Price = ? WHERE ItemID = ?" ;

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, Name);
            pstm.setString(2, Man_Date);
            pstm.setString(3, Exp_Date);
            pstm.setString(4,Supplier_name);
            pstm.setString(5,Type);
            pstm.setString(6,Supplier_contact);
            pstm.setString(7,Description);
            pstm.setString(8,Quantity);
            pstm.setDouble(9,Price);
            pstm.setString(10,ItemID);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
            }

        }
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/SupplieUpdateForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();

    }
}
