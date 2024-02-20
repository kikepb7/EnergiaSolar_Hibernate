package dto.panelDTO;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ModelPricePowerDTO {

    // 1. Attributes
    @Getter @Setter @Expose
    private String model;
    @Getter @Setter @Expose
    private int nominalPower;
    @Getter @Setter @Expose
    private double price;


    // 2. Constructors
    public ModelPricePowerDTO() {}

    public ModelPricePowerDTO(String model, int nominalPower, double price) {
        this.model = model;
        this.nominalPower = nominalPower;
        this.price = price;
    }
}
