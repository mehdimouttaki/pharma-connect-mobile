package ma.pharmaconnect.pharmaconnect.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewShowDTO {
    private Integer id;
    private String label;
    private Integer rate;
    private ClientShowDTO client;
    private DeliveryManShowDTO deliveryMan;
    private OrderShowDTO order;
}