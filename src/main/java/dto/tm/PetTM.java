package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class PetTM implements Comparable<PetTM>{
    private String PetID;
    private String Name;
    private String CustomerID;
    private String Type;
    private String Breed;
    private String Gender;
    private String DOB;
    private int Age;
    private String Address;
    private String Contact;

    @Override
    public int compareTo(PetTM o) {
        return PetID.compareTo(o.getPetID());
    }
}
