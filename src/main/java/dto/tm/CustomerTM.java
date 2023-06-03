package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomerTM {
    private String CustomerID;
    private String CustTitle;
    private String Name;
    private String NIC;
    private String DOB;
    private int age;
    private String Gender;
    private String contact;
    private String email;
    private String address;


}
