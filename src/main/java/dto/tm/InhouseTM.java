package dto.tm;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class InhouseTM {
    private String Inhouse_ID;
    private String Pet_ID;
    private String Customer_ID;
    private String Admitted_Date;
    private String Time;
    private String Discharge_Date;
    private String Description;
    private String contact;
}
