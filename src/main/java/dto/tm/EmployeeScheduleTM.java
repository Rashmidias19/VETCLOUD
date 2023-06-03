package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeScheduleTM {
    private String Schedule_ID;
    private String Employee_ID;
    private String Name;
    private String Date;
    private String Time;
    private String WorkTime;
    private String Shift;
    private String OT;
}
