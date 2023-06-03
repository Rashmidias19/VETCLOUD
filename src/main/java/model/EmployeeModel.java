package model;

import com.mysql.cj.jdbc.Blob;
import db.DBConnection;
import dto.Customer;
import dto.Employee;
import dto.Pet;
import util.CrudUtil;

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
}
