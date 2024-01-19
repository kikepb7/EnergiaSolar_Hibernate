package entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "User")
@ToString @EqualsAndHashCode
public class User {
    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    @Getter @Setter
    private String name;

    @Column(name = "lastName", nullable = false, length = 40)
    @Getter @Setter
    private String lastName;

    @Column(name= "email", nullable = false)
    @Getter @Setter
    private String email;

    @Column(name = "password", nullable = false)
    @Getter @Setter
    private String password;

    @Column(name = "registrationDate", nullable = false)
    @Getter @Setter
    private LocalDate registrationDate;

    @Column(name = "purchaseDate", nullable = false)
    @Getter @Setter
    private LocalDate purchaseDate;


    // 2. Constructors
    public User() {}

    public User(Long id, String name, String lastName, String email, String password, LocalDate registrationDate, LocalDate purchaseDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.purchaseDate = purchaseDate;
    }
}
