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
@Table(name = "Project")
@ToString
@EqualsAndHashCode
public class Project {

    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Expose
    private Long id;

    @Column(name = "name", length = 100, unique=true, nullable = false)
    @Getter @Setter @Expose
    private String name;

    @Column(name = "address", nullable = false)
    @Getter @Setter @Expose
    private String address;

    @Column(name = "generation_capacity", nullable = false)
    @Getter @Setter @Expose
    private int generationCapacity;

    @Column(name = "instalation_date", nullable = false)
    @Getter @Setter @Expose
    private LocalDate instalationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_project_user"))
    @Getter @Setter
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "calculation_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "calculation_id")
    )
    @Getter @Setter
    private List<Calculation> calculations = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "panel_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "panel_id")
    )
    @Getter @Setter
    private List<Panel> panels = new ArrayList<>();


    // 2. Constructors
    public Project() {}

    public Project(Long id, String name, String address, int generationCapacity, LocalDate instalationDate, User user, List<Calculation> calculations, List<Panel> panels) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.generationCapacity = generationCapacity;
        this.instalationDate = instalationDate;
        this.user = user;
        this.calculations = calculations;
        this.panels = panels;
    }
}
