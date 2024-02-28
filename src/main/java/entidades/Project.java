package entidades;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Project")
@ToString @EqualsAndHashCode
public class Project implements Serializable {

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

    @Column(name = "image")
    @Getter @Setter @Expose
    private String image;

    @Column(name = "description", length = 1000)
    @Getter @Setter @Expose
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_project_user"))
    @Getter @Setter
    private User user;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
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

    public Project(Long id, String name, String address, int generationCapacity, LocalDate instalationDate, String image, String description, User user, List<Calculation> calculations, List<Panel> panels) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.generationCapacity = generationCapacity;
        this.instalationDate = instalationDate;
        this.image = image;
        this.description = description;
        this.user = user;
        this.calculations = calculations;
        this.panels = panels;
    }
}
