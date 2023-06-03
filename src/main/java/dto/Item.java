package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Item {
    private String ItemID;
    private String Man_Date;
    private String Exp_Date;
    private String Supplier_name;
    private String Type;
    private String Supplier_contact;
    private String Description;
    private String Quantity;
    private double Price;
    private String Name;

    public Item(String string, double anInt) {

        this.Name=string;
        this.Price=anInt;
    }


}
