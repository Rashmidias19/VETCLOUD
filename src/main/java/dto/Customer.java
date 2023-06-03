package dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String CustomerID;
    private String CustTitle;
    private String CustName;
    private String NIC;
    private String DOB;
    private int age;
    private String Gender;
    private String contact;
    private String email;
    private String address;


    public Customer(String customerID, String custName, String NIC, LocalDate DOB, int age, String gender, String contact, String string, String string1) {
    }
}
