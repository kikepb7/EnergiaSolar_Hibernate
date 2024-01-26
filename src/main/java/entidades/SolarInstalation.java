package entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "SolarInstalation")
@ToString
@EqualsAndHashCode
public class SolarInstalation {

    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "name", length = 100, unique=true, nullable = false)
    @Getter @Setter
    private String name;

    @Column(name = "address", nullable = false)
    @Getter @Setter
    private String address;

    @Column(name = "generation_capacity", nullable = false)
    @Getter @Setter
    private int generationCapacity;

    @Column(name = "instalation_date", nullable = false)
    @Getter @Setter
    private LocalDate instalationDate;

    @OneToMany(mappedBy = "panel",fetch = FetchType.LAZY)
    @Getter @Setter
    private List<Panel> storage = new ArrayList<>();


    // 2. Constructors
    public SolarInstalation() {}

    public SolarInstalation(Long id, String name, String address, int generationCapacity, LocalDate instalationDate, List<Panel> storage) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.generationCapacity = generationCapacity;
        this.instalationDate = instalationDate;
        this.storage = storage;
    }
}
