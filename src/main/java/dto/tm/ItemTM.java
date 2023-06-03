package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemTM {
    private String Item_ID;
    private String Name;
    private String Man_Date;
    private String Exp_Date;
    private String Supplier_name;
    private String Type;
    private String Contact;
    private String Description;
    private String Quantity;
    private double Price;

}
