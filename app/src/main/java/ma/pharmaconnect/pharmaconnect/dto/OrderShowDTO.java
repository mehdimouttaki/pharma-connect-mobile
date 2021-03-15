package ma.pharmaconnect.pharmaconnect.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderShowDTO {
    private Integer id;
    private Double price;
    private String address;
    private ClientShowDTO client;
    private DeliveryManShowDTO deliveryMan;
    private OrderStatus orderStatus;

}
