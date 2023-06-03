package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class UserTM {
    private String UserID;
    private String UserName;
    private String Password;
    private String Email;
}
