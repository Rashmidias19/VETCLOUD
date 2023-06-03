package dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CartTM {
    private String ItemID;
    private String Name;
    private int Quantity;
    private double Price;
}
