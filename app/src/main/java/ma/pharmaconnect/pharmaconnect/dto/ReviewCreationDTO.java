package ma.pharmaconnect.pharmaconnect.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewCreationDTO {
    private String label;
    private Integer rate;
    private Integer deliveryManId;
    private Integer orderId;
}