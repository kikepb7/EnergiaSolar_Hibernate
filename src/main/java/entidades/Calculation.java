package entidades;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Calculation")
@ToString
@EqualsAndHashCode
public class Calculation {

    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Expose
    private Long id;

    @Column(name = "sun_hours")
    @Getter @Setter
    private double sunHours;

    @Column(name = "daily_consumption")
    @Getter @Setter
    private double dailyConsumption;

    @Column(name = "daily_production")
    @Getter @Setter
    private double dailyProduction;

    @Column(name = "calculation_date")
    @Getter @Setter
    private LocalDate calculationDate;

    @Column(name = "comment")
    @Getter @Setter
    private String comment;

    @ManyToMany(mappedBy = "calculations")
    private List<Project> projects = new ArrayList<>();


    // 2. Constructors
    public Calculation() {}

    public Calculation(Long id, double sunHours, double dailyConsumption, double dailyProduction, LocalDate calculationDate, String comment, List<Project> projects) {
        this.id = id;
        this.sunHours = sunHours;
        this.dailyConsumption = dailyConsumption;
        this.dailyProduction = dailyProduction;
        this.calculationDate = calculationDate;
        this.comment = comment;
        this.projects = projects;
    }

    /*
    CONSUMO DIARIO --> consumo anual kWh / Horas de luz h
    NÚMERO DE PLACAS --> producción diaria / potencia de la placa * 1000 (redondeamos al alza)
     */
}
