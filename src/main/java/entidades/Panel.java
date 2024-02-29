package entidades;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Panel")
@ToString @EqualsAndHashCode
public class Panel implements Serializable {

    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Expose
    private Long id;

    @Column(name = "brand", nullable = false)
    @Getter @Setter @Expose
    private String brand;

    @Column(name = "category")
    @Getter @Setter @Expose
    private String category;

    @Column(name = "production_date")
    @Getter @Setter @Expose
    private LocalDate productionDate;

    @Column(name = "efficiency")
    @Getter @Setter @Expose
    private double efficiency;

    @Column(name = "image")
    @Getter @Setter @Expose
    private String image;

    @Column(name = "model", unique = true, nullable = false)
    @Getter @Setter @Expose
    private String model;

    @Column(name = "nominal_power")
    @Getter @Setter @Expose
    private int nominalPower;

    @Column(name = "price", nullable = false)
    @Getter @Setter @Expose
    private double price;

    @ManyToMany(mappedBy = "panels")
    @Getter @Setter
    private List<Project> projects = new ArrayList<>();


    // 2. Constructors
    public Panel() {}

    public Panel(Long id, String brand, String category, LocalDate productionDate, double efficiency, String image, String model, int nominalPower, double price, List<Project> projects) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.productionDate = productionDate;
        this.efficiency = efficiency;
        this.image = image;
        this.model = model;
        this.nominalPower = nominalPower;
        this.price = price;
        this.projects = projects;
    }
}
