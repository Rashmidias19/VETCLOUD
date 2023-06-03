package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class BillTM {
    private String Bill_ID;
    private String Customer_ID;
    private String Date;
    private String Time;
    private double Amount;
    private String Contact;
    private String Email;
    private String Description;

}
