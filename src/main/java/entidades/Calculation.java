package entidades;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    @Getter
    @Setter
    @Expose
    private Long id;

    @ManyToMany(mappedBy = "calculations")
    private List<Project> projects = new ArrayList<>();


    // 2. Constructors
    public Calculation() {}

    public Calculation(Long id, List<Project> projects) {
        this.id = id;
        this.projects = projects;
    }
}
