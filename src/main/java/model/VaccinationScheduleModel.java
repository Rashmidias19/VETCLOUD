package model;

import db.DBConnection;
import dto.OperationSchedule;
import dto.VaccinationSchedule;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccinationScheduleModel {

    public static String getNextVaccId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT VaccinationID FROM vaccinationschedule ORDER BY VaccinationID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitVaccId(resultSet.getString(1));
        }
        return splitVaccId(null);
    }

    public static boolean save(VaccinationSchedule vaccinationSchedule) throws SQLException, ClassNotFoundException {
        String sql =  "INSERT INTO Vaccinationschedule(VaccinationID, PetID,CustomerID,Date,Time,Description,Contact)" +
                "VALUES(?, ?, ?, ?,?,?,?)";
        return CrudUtil.execute(
                sql,
                vaccinationSchedule.getVaccinationID(),
                vaccinationSchedule.getPetID(),
                vaccinationSchedule.getCustomerID(),
                vaccinationSchedule.getDate(),
                vaccinationSchedule.getTime(),
                vaccinationSchedule.getDescription(),
                vaccinationSchedule.getContact());
    }


    private static String splitVaccId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("V000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "V000" + id;
        }
        return "V0001";
    }

    public static List<VaccinationSchedule> getAll() throws SQLException {
        List<VaccinationSchedule> data = new ArrayList<>();

        String sql = "SELECT * FROM VaccinationSchedule";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new VaccinationSchedule(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }
}
