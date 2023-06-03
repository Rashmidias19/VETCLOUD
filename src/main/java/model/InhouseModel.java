package model;

import db.DBConnection;
import dto.Bill;
import dto.Customer;
import dto.Inhouse;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InhouseModel {

    public static String getNextInId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT InhouseID FROM Inhouse ORDER BY InhouseID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitInId(resultSet.getString(1));
        }
        return splitInId(null);
    }

    private static String splitInId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("IH000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "IH000" + id;
        }
        return "IH0001";
    }

    public static List<Inhouse> getAll() throws SQLException {
        List<Inhouse> data = new ArrayList<>();

        String sql = "SELECT * FROM Inhouse";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Inhouse(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return data;
    }

    public static List<String> loadInhouseID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT InhouseID FROM Inhouse");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Inhouse searchById(String inhouseID) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Inhouse WHERE InhouseID =?");
        pstm.setString(1, inhouseID);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new Inhouse(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
        }
        return null;
    }
}
