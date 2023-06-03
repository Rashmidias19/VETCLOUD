package dto;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Inhouse {
    private String InhouseID;
    private String PetID;
    private String CustomerID;
    private String AdmittedDate;
    private String time;
    private String DischargeDate;
    private String Description;
    private String contact;

}
