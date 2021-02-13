package ma.pharmaconnect.pharmaconnect.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ClientShowDTO {
    private Integer id;
    private String lastName;
    private String firstName;
    private String username;
    private String phone;
    private String role;
    private Date createdAt;
    private String status;
}
