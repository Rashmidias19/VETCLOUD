package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String UserID;
    private String UserName;
    private String Password;
    private String email;
}
