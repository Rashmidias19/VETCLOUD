package model;

import db.DBConnection;
import dto.Employee;
import dto.Item;
import javafx.scene.control.Alert;
import util.CrudUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemModel {


    public static List<String> loadItemID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT ItemID FROM Item");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Item searchById(String ID) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Item WHERE ItemID =?");
        pstm.setString(1, ID);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new Item(

                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)
            );
        }
        return null;
    }

    public static String getNextItemId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT ItemID FROM item ORDER BY ItemID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitItemId(resultSet.getString(1));
        }
        return splitItemId(null);
    }

    private static String splitItemId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("I000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "I000" + id;
        }
        return "I0001";
    }

    public static List<Item> getAll() throws SQLException {
        List<Item> data = new ArrayList<>();

        String sql = "SELECT * FROM Item";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)

            ));
        }
        return data;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Item WHERE ItemID = ?",id);
    }

    public static boolean save(Item item) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item(ItemID,Name,Man_Date,Exp_Date,Supplier_name,Type,Supplier_contact,Description,Quantity,Price)" +
                "VALUES(?, ?, ?, ?,?,?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                item.getItemID(),
                item.getName(),
                item.getMan_Date(),
                item.getExp_Date(),
                item.getSupplier_name(),
                item.getType(),
                item.getSupplier_contact(),
                item.getDescription(),
                item.getQuantity(),
                item.getPrice());
    }



    public static boolean update(Item item) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET Name = ?,  Man_Date = ?, Exp_Date = ?, Supplier_name = ?, Type = ?, Supplier_contact = ?, Description = ?, Quantity = ?, Price = ? WHERE ItemID = ?" ;

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, item.getName());
        pstm.setString(2, item.getMan_Date());
        pstm.setString(3, item.getExp_Date());
        pstm.setString(4,item.getSupplier_name());
        pstm.setString(5,item.getType());
        pstm.setString(6,item.getSupplier_contact());
        pstm.setString(7,item.getDescription());
        pstm.setString(8,item.getQuantity());
        pstm.setDouble(9,item.getPrice());
        pstm.setString(10,item.getItemID());

        boolean isUpdated = pstm.executeUpdate() > 0;
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
        }
        return isUpdated;
    }
}
