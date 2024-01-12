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

    @Column(name = "model", nullable = false)
    @Getter @Setter
    private String model;

    @Column(name = "brand")
    @Getter @Setter
    private String brand;

    @Column(name = "material")
    @Getter @Setter
    private String material;

    @Column(name = "power")
    @Getter @Setter
    private double power;

    @Column(name = "efficiency")
    @Getter @Setter
    private double efficiency;

    @Column(name = "fabrication_date")
    @Getter @Setter
    private LocalDate fabricationDate;

    @Column(name = "image")
    @Getter @Setter
    private String image;

    @Column(name = "category")
    @Getter @Setter
    private String category;

    @Column(name = "price")
    @Getter @Setter
    private double price;


    // 2. Constructors
    public Panel() {}


}
