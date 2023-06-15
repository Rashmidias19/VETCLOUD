package model;

import db.DBConnection;
import dto.Pet;
import javafx.scene.control.Alert;
import util.CrudUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetModel {

    public static List<String> loadPetID() throws SQLException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT PetID FROM Pet");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Pet searchById(String ID) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Pet WHERE PetID =?");
        pstm.setString(1, ID);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Pet(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getInt(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getBlob(11)
            );
        }
        return null;
    }

    public static List<Pet> getAll() throws SQLException {
        List<Pet> data = new ArrayList<>();

        String sql = "SELECT * FROM Pet";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Pet(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getInt(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getBlob(11)
            ));
        }
        return data;
    }

    public static String getNextPetId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT PetID FROM pet ORDER BY PetID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("PetID");
            int newCustomerId = Integer.parseInt(id.replace("P00-", "")) + 1;
            return String.format("P00-%03d", newCustomerId);
        } else {
            return "P00-001";
        }
    }


    public static boolean save(Pet pet, FileInputStream inp, File file) throws SQLException, FileNotFoundException {
        String sql = "INSERT INTO Pet(PetID,Name,CustomerID,Type ,Breed,Gender,DOB,age,address,contact,picture )" +
                "VALUES(?, ?, ?, ?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, pet.getPetID());
        pstm.setString(2, pet.getName());
        pstm.setString(3, pet.getCustomerID());
        pstm.setString(4, pet.getType());
        pstm.setString(5, pet.getBreed());
        pstm.setString(6, pet.getGender());
        pstm.setDate(7, Date.valueOf(pet.getDOB()));
        pstm.setInt(8, pet.getAge());
        pstm.setString(9, pet.getAddress());
        pstm.setString(10, pet.getContact());
        inp = new FileInputStream(file);
        pstm.setBinaryStream(11, (InputStream) inp, (int) file.length());
        int affectedRows = pstm.executeUpdate();
        boolean isSaved=false;
        if (affectedRows > 0) {
            isSaved=true;
        }
        return isSaved;
    }

    public static boolean update(Pet pet) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Pet SET Name = ?, CustomerID = ?, Type = ?, Breed = ?, Gender = ?, DOB = ?, age = ?, address = ?, contact = ? WHERE PetID = ?");

        pstm.setString(1, pet.getName());
        pstm.setString(2, pet.getCustomerID());
        pstm.setString(3, pet.getType());
        pstm.setString(4, pet.getBreed());
        pstm.setString(5, pet.getGender());
        pstm.setString(6, pet.getDOB());
        pstm.setInt(7, pet.getAge());
        pstm.setString(8, pet.getAddress());
        pstm.setString(9, pet.getContact());
        pstm.setString(10, pet.getPetID());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Pet WHERE PetID = ?",id);
    }

    public static boolean exist(String id) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT PetID FROM Pet WHERE PetID=?",id);
        return rst.next();
    }

    public static String generate() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT PetID FROM Pet ORDER BY PetID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("PetID");
            int newPetId = Integer.parseInt(id.replace("P00-", "")) + 1;
            return String.format("P00-%03d", newPetId);
        } else {
            return "P00-001";
        }
    }
}
