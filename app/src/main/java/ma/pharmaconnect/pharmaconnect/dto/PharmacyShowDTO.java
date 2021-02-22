package ma.pharmaconnect.pharmaconnect.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString

public class PharmacyShowDTO implements Serializable {

    private Integer id;
    private String name;
    private String address;
    private String pharmacist;
    private Double lat;
    private Double lng;
    private FullCityDTO city;


}
