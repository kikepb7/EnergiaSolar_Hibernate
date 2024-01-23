package dto.panelDTO;

import lombok.Getter;
import lombok.Setter;

public class ModelPricePowerDTO {

    // 1. Attributes
    @Getter @Setter
    private String model;
    @Getter @Setter
    private int nominalPower;
    @Getter @Setter
    private double price;


    // 2. Constructors
    public ModelPricePowerDTO() {}

    public ModelPricePowerDTO(String model, int nominalPower, double price) {
        this.model = model;
        this.nominalPower = nominalPower;
        this.price = price;
    }
}
