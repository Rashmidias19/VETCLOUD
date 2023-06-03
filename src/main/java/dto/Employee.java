package dto;

import lombok.*;

import java.sql.Blob;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Employee {
    private String EmployeeID;
    private String Name;
    private String UserID;
    private String DOB;
    private String NIC;
    private int Age;
    private String Gender;
    private String address;
    private String Salary;
    private String Contact;
    private String email;
    private Blob picture;
}
