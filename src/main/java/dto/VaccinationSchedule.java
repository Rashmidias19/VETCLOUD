package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class VaccinationSchedule {
    private String VaccinationID;
    private String PetID;
    private String CustomerID;
    private String Date;
    private String Time;
    private String Description;
    private String Contact;
}
