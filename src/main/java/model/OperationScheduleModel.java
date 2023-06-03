package model;

import db.DBConnection;
import dto.EmployeeSchedule;
import dto.OperationSchedule;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperationScheduleModel {

    public static String getNextOpId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT OperationID FROM operationschedule ORDER BY OperationID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOpId(resultSet.getString(1));
        }
        return splitOpId(null);
    }

    private static String splitOpId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("OP000");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "OP000" + id;
        }
        return "OP0001";
    }

    public static List<OperationSchedule> getAll() throws SQLException {
        List<OperationSchedule> data = new ArrayList<>();

        String sql = "SELECT * FROM OperationSchedule";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new OperationSchedule(
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
}
