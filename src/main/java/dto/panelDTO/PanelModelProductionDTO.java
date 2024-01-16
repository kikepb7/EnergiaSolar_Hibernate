package dto.panelDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PanelModelProductionDTO {

    // 1. Attributes
    @Getter @Setter
    private String model;
    @Getter @Setter
    private LocalDate productionDate;


    // 2. Constructors
    public PanelModelProductionDTO() {}

    public PanelModelProductionDTO(String model, LocalDate productionDate) {
        this.model = model;
        this.productionDate = productionDate;
    }
}

