package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class OperationSchedule {
    private String OperationID;
    private String PetID;
    private String CustomerID;
    private String Date;
    private String Time;
    private String Description;
    private String Hours;
    private String Contact;
}
