package ma.pharmaconnect.pharmaconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreationDTO {
    private String lastName;
    private String firstName;
    private String username;
    private String phone;
    private String password;
}
