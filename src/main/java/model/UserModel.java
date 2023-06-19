package model;

import db.DBConnection;
import dto.Customer;
import dto.User;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserModel {
    private static final String URL = "jdbc:mysql://localhost:3306/VETCLOUD";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<String> loadUserID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT UserID FROM User");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static List<User> getAll() throws SQLException {
        List<User> data = new ArrayList<>();

        String sql = "SELECT * FROM User";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    public static User searchById(String userID) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM User WHERE UserID =?");
        pstm.setString(1, userID);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            );
        }
        return null;
    }

    public static boolean exist(String id) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT UserID FROM User WHERE UserID=?",id);
        return rst.next();
    }

    public static boolean save(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO User(UserID,UserName,password,email)" +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                user.getUserID(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail());
    }

    public static String getNextUserId() throws SQLException {
            ResultSet rst = CrudUtil.execute("SELECT UserID FROM User ORDER BY UserID DESC LIMIT 1");
            if (rst.next()) {
                String id = rst.getString("UserID");
                int newCustomerId = Integer.parseInt(id.replace("U00-", "")) + 1;
                return String.format("U00-%03d", newCustomerId);
            } else {
                return "U00-001";
            }
    }


    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM User WHERE UserID = ?",id);
    }


    public static boolean update(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE User SET UserName = ?,  Password = ?, email = ? WHERE UserID = ?",
                dto.getUserName(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getUserID()
        );
    }
}
