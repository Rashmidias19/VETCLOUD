package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeSchedule {
    private String ScheduleID;
    private String EmployeeID;
    private String Name;
    private String Date;
    private String Time;
    private String WorkTime;
    private String Shift;
    private String OT;
}
