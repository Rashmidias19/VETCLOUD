package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Bill {
    private String BillID;
    private String CustomerID;
    private String Date;
    private String Time;
    private double Amount ;
    private String contact;
    private String email;
    private String Description;


}
