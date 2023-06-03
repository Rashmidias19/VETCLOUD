package model;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpSchedModel {
    public static boolean save(String schedID, String empID) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO EmpSched(ScheduleID,EmployeeID)" +
                    "VALUES(?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,schedID);
            pstm.setString(2, empID);

        return pstm.executeUpdate() > 0;
        }

}
