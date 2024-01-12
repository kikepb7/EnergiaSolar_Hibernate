package dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
public class PanelDTO {

    // 1. Attributes
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String imageURL;


    // 2. Constructors
    public PanelDTO() {}

    public PanelDTO(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }
}
