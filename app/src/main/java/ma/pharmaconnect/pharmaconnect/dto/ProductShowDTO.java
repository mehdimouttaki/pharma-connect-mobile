package ma.pharmaconnect.pharmaconnect.dto;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductShowDTO {
    private Integer id;
    private String name;
    private String code;
    private Boolean prescription;
    private Double price;
    private boolean selected = false;




    @Override
    public String toString() {
        return name + "( " + code + " )";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductShowDTO that = (ProductShowDTO) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
