package model;

import db.DBConnection;
import dto.Customer;
import dto.User;
import org.bytedeco.opencv.presets.opencv_core;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomerModel {

    public static List<String> loadCustomerID() throws SQLException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT CustomerID FROM Customer");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
    public static Customer searchById(String ID) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Customer WHERE CustomerID =?");
        pstm.setString(1, ID);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10)

            );
        }
        return null;
    }


    public static List<Customer> getAll() throws SQLException {
        List<Customer> data = new ArrayList<>();

        String sql = "SELECT * FROM Customer";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
            ));
        }
        return data;
    }

    public static String getNextCustomerId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT CustomerID FROM customer ORDER BY CustomerID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitCustomerId(resultSet.getString(1));
        }
        return splitCustomerId(null);
    }

    private static String splitCustomerId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("C000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "C000" + id;
        }
        return "C0001";
    }

    public static boolean delete(String id) throws SQLException {
        try(Connection con =DBConnection.getInstance().getConnection()){

            String sql = "DELETE FROM Customer WHERE CustomerID = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer(CustomerID,CustTitle,CustName,NIC,DOB,age,Gender,contact,email, address)" +
                "VALUES(?, ?, ?, ?,?,?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                customer.getCustomerID(),
                customer.getCustTitle(),
                customer.getCustName(),
                customer.getNIC(),
                customer.getDOB(),
                customer.getAge(),
                customer.getGender(),
                customer.getContact(),
                customer.getEmail(),
                customer.getAddress());
    }

    public static boolean update(Customer customer) throws SQLException {

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET CustTitle = ?,  CustName = ?, NIC = ?, DOB = ?, age = ?, Gender = ?, contact = ?, email = ?, address = ? WHERE CustomerID = ?");

        pstm.setString(1,customer.getCustTitle());
        pstm.setString(2, customer.getCustName());
        pstm.setString(3, customer.getNIC());
        pstm.setString(4,customer.getDOB());
        pstm.setInt(5,customer.getAge());
        pstm.setString(6,customer.getGender());
        pstm.setString(7,customer.getContact());
        pstm.setString(8,customer.getEmail());
        pstm.setString(9,customer.getAddress());
        pstm.setString(10,customer.getCustomerID());

        return pstm.executeUpdate() > 0;

    }
}