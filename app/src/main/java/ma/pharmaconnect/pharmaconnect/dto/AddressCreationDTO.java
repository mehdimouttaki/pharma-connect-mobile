package ma.pharmaconnect.pharmaconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreationDTO {
    private String number;
    private String street;
    private String detail;
    private String city;
    private String zip;

    @Override
    public String toString() {
        return number + ", " + street + '\n' + detail + '\n' + zip + " " + city;
    }
}
