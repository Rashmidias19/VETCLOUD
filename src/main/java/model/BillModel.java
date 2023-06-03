package model;

import db.DBConnection;
import dto.Bill;
import dto.Item;
import util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BillModel {

    public static List<Bill> getAll() throws SQLException {

        try(Connection con=DBConnection.getInstance().getConnection()) {
            String sql = "SELECT * FROM Bill";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery(sql);
            List<Bill> data = new ArrayList<>();
            while (resultSet.next()) {
                data.add(new Bill(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                ));
            }
            return data;
        }
    }

    public static List<String> loadBillID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT BillID FROM Bill");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static String getNextBillId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT BillID FROM Bill ORDER BY BillID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitBillId(resultSet.getString(1));
        }
        return splitBillId(null);
    }

    private static String splitBillId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("B000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "B000" + id;
        }
        return "B0001";
    }


    public static boolean save(Bill bill) throws SQLException {
        String sql = "INSERT INTO Bill(BillID, CustomerID, Date, Time, Amount, Contact, email, Description) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                bill.getBillID(),
                bill.getCustomerID(),
                bill.getDate(),
                bill.getTime(),
                bill.getAmount(),
                bill.getContact(),
                bill.getEmail(),
                bill.getDescription());
    }

    public static boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Bill WHERE BillID = ?",id);
    }
}
