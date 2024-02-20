package dto.panelDTO;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
public class PanelDTO {

    // 1. Attributes
    @Getter @Setter @Expose
    private String model;
    @Getter @Setter @Expose
    private String image;
    @Getter @Setter @Expose
    private int nominalPower;
    @Getter @Setter @Expose
    private double price;


    // 2. Constructors
    public PanelDTO() {}

    public PanelDTO(String model, String image, int nominalPower, double price) {
        this.model = model;
        this.image = image;
        this.nominalPower = nominalPower;
        this.price = price;
    }
}
