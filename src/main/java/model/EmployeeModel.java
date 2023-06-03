package model;

import com.mysql.cj.jdbc.Blob;
import db.DBConnection;
import dto.Customer;
import dto.Employee;
import dto.Pet;
import javafx.scene.control.Alert;
import util.CrudUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static Employee searchById(String ID) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Employee WHERE EmployeeID =?");
        pstm.setString(1, ID);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getBlob(12)
            );
        }
        return null;
    }

    public static List<String> loadEmployeeID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT EmployeeID FROM Employee");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static List<Employee> getAll() throws SQLException {
        List<Employee> data = new ArrayList<>();

        String sql = "SELECT * FROM Employee";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getBlob(12)
            ));
        }
        return data;
    }

    public static String getNextEmpId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT EmployeeID FROM employee ORDER BY EmployeeID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitEmpId(resultSet.getString(1));
        }
        return splitEmpId(null);
    }

    private static String splitEmpId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("E000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "E000" + id;
        }
        return "E0001";
    }

    public static boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Employee WHERE EmployeeID = ?",id);
    }

    public static boolean saveWithPicture(Employee employee, FileInputStream inp, File file) throws SQLException, FileNotFoundException {
        String sql = "INSERT INTO Employee(EmployeeID,Name,UserID,DOB,NIC,Age,Gender,address,Salary,contact,email,picture)" +
                "VALUES(?, ?, ?, ?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, employee.getEmployeeID());
        pstm.setString(2, employee.getName());
        pstm.setString(3, employee.getUserID());
        pstm.setString(4,employee.getDOB());
        pstm.setString(5, employee.getNIC());
        pstm.setInt(6,employee.getAge());
        pstm.setString(7,employee.getGender());
        pstm.setString(8, employee.getAddress());
        pstm.setDouble(9, Double.parseDouble(employee.getSalary()));
        pstm.setString(10, employee.getContact());
        pstm.setString(11,employee.getEmail());
        inp = new FileInputStream(file);
        pstm.setBinaryStream(12, (InputStream) inp, (int) file.length());
        int affectedRows = pstm.executeUpdate();
        boolean isSaved=false;
        if (affectedRows > 0) {
            isSaved=true;
        }
        return isSaved;

    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET Name = ?,  UserID = ?, DOB = ?, NIC = ?, Age = ?, gender = ?, address = ?, salary = ?, contact = ?, email = ? WHERE EmployeeID = ?" ;

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, employee.getName());
        pstm.setString(2, employee.getUserID());
        pstm.setString(3, employee.getDOB());
        pstm.setString(4,employee.getNIC());
        pstm.setInt(5,employee.getAge());
        pstm.setString(6,employee.getGender());
        pstm.setString(7,employee.getAddress());
        pstm.setString(8,employee.getSalary());
        pstm.setString(9,employee.getContact());
        pstm.setString(10,employee.getEmail());
        pstm.setString(11,employee.getEmployeeID());

        boolean isUpdated = pstm.executeUpdate() > 0;
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
        }
        return isUpdated;
    }
}
