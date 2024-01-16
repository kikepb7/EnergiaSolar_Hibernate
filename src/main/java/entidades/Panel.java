package entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Panel")
@ToString @EqualsAndHashCode
public class Panel implements Serializable {

    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "brand", nullable = false)
    @Getter @Setter
    private String brand;

    @Column(name = "category")
    @Getter @Setter
    private String category;

    @Column(name = "production_date")
    @Getter @Setter
    private LocalDate productionDate;

    @Column(name = "efficiency")
    @Getter @Setter
    private String efficiency;

    @Column(name = "image")
    @Getter @Setter
    private String image;

    @Column(name = "model", unique = true, nullable = false)
    @Getter @Setter
    private String model;

    @Column(name = "nominal_power")
    @Getter @Setter
    private int nominalPower;

    @Column(name = "price", nullable = false)
    @Getter @Setter
    private double price;


    // 2. Constructors
    public Panel() {}

    public Panel(Long id, String brand, String category, LocalDate productionDate, String efficiency, String image, String model, int nominalPower, double price) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.productionDate = productionDate;
        this.efficiency = efficiency;
        this.image = image;
        this.model = model;
        this.nominalPower = nominalPower;
        this.price = price;
    }
}
