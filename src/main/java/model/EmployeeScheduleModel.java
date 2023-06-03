package model;

import db.DBConnection;
import dto.EmployeeSchedule;
import dto.Inhouse;
import util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeScheduleModel {
    public static List<EmployeeSchedule> getAll() throws SQLException {
        List<EmployeeSchedule> data = new ArrayList<>();

        String sql = "SELECT * FROM EmployeeSchedule";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new EmployeeSchedule(
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
    public static String getNextSchedId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT ScheduleID FROM employeeschedule ORDER BY ScheduleID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitSchedId(resultSet.getString(1));
        }
        return splitSchedId(null);
    }

    private static String splitSchedId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("ES000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "ES000" + id;
        }
        return "ES0001";
    }

    public static boolean addSchedule(String schedID, String EmpID, String name, LocalDate date, String time, String workTime, String shift, String ot) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved =save(schedID,EmpID,name,date,time,workTime,shift,ot);
            if(isSaved) {
                    boolean isUpdated = EmpSchedModel.save(schedID,EmpID);
                    if(isUpdated) {
                        con.commit();
                        return true;
                    }
                }

            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }

    }

    private static boolean save(String schedID, String empID, String name, LocalDate date, String time, String workTime, String shift, String ot) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO EmployeeSchedule(ScheduleID,EmployeeID,Name,Date,Time,WorkTime,Shift,OT)" +
                    "VALUES(?, ?, ?, ?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,schedID);
            pstm.setString(2, empID);
            pstm.setString(3, name);
            pstm.setDate(4, java.sql.Date.valueOf(date));
            pstm.setTime(5, java.sql.Time.valueOf(time));
            pstm.setString(6,workTime);
            pstm.setString(7,shift);
            pstm.setString(8,ot);


        return pstm.executeUpdate() > 0;


    }

}
