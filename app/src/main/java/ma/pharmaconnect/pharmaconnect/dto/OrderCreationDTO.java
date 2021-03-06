package ma.pharmaconnect.pharmaconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationDTO {

    private List<String> codes = new ArrayList<>();
    private String deliveryAddress;

    public OrderCreationDTO(String code, String deliveryAddress) {
        this.codes = new ArrayList<>(Collections.singletonList(code));
        this.deliveryAddress = deliveryAddress;
    }
}
