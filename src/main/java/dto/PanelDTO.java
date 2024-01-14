package dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
public class PanelDTO {

    // 1. Attributes
    @Getter @Setter
    private String model;
    @Getter @Setter
    private String image;


    // 2. Constructors
    public PanelDTO() {}

    public PanelDTO(String model, String image) {
        this.model = model;
        this.image = image;
    }
}
