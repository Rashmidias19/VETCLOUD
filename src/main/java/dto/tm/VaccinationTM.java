package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class VaccinationTM {
    private String Vaccination_ID;
    private String Pet_ID;
    private String Customer_ID;
    private String Date;
    private String Time;
    private String Description;
    private String Contact;
}
