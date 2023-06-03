package dto;

import lombok.*;

import java.sql.Blob;
import java.time.LocalDate;

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

    public Employee(String employeeID, String name, String userID, LocalDate DOB, String NIC, int age, String gender, String address, String salary, String contact, String email) {
        EmployeeID = employeeID;
        Name = name;
        UserID = userID;
        this.DOB = String.valueOf(DOB);
        this.NIC = NIC;
        Age = age;
        Gender = gender;
        this.address = address;
        Salary = salary;
        Contact = contact;
        this.email = email;
    }

    public Employee(String employeeID, String name, String userID, String dob, String nic, int age, String gender, String address, String salary, String contact, String email) {
        EmployeeID = employeeID;
        Name = name;
        UserID = userID;
        this.DOB = dob;
        this.NIC = nic;
        Age = age;
        Gender = gender;
        this.address = address;
        Salary = salary;
        Contact = contact;
        this.email = email;
    }
}
