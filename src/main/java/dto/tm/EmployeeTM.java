package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTM {
    private String EmployeeID;
    private String Name;
    private String UserID;
    private String DOB;
    private String NIC;
    private int Age;
    private String Gender;
    private String Address;
    private String Salary;
    private String Contact;
    private String Email;
}
