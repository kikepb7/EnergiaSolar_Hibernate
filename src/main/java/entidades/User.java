package entidades;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
@ToString @EqualsAndHashCode
public class User implements Serializable {

    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Expose
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    @Getter @Setter @Expose
    private String name;

    @Column(name = "lastName", nullable = false, length = 40)
    @Getter @Setter @Expose
    private String lastName;

    @Column(name = "phone", nullable = false, length = 20)
    @Getter @Setter @Expose
    private String phone;

    @Column(name= "email", nullable = false, unique = true)
    @Getter @Setter @Expose
    private String email;

    @Column(name = "password", nullable = false)
    @Getter @Setter @Expose
    private String password;

    @Column(name = "image")
    @Getter @Setter @Expose
    private String image;

    @Column(name = "admin", nullable = false)
    @Getter @Setter @Expose
    private boolean admin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Getter @Setter
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Getter @Setter
    private List<Report> reports = new ArrayList<>();


    // 2. Constructors
    public User() {}

    public User(Long id, String name, String lastName, String phone, String email, String password, String image, boolean admin, List<Project> projects, List<Report> reports) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.image = image;
        this.admin = admin;
        this.projects = projects;
        this.reports = reports;
    }
}
