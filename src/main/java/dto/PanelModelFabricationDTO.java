package dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PanelModelFabricationDTO {
    // 1. Attributes
    @Getter @Setter
    private String model;
    @Getter @Setter
    private LocalDate fabricationDate;


    // 2. Constructors
    public PanelModelFabricationDTO() {}

    public PanelModelFabricationDTO(String model, LocalDate fabricationDate) {
        this.model = model;
        this.fabricationDate = fabricationDate;
    }
}

