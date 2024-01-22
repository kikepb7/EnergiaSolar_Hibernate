package entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Token")
@ToString @EqualsAndHashCode
public class Token {
    // 1. Attributess
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "apikey", nullable = false, length = 40)
    @Getter @Setter
    private String apikey;

    @Column(name = "uses", nullable = false)
    @Getter @Setter
    private int uses;

    @Column(name = "isActive", nullable = false)
    @Getter @Setter
    private boolean isActive;

    @Column(name = "allowRead", nullable = false)
    @Getter @Setter
    private boolean allowRead;


    // 2. Constructors
    public Token() {}

    public Token(Long id, String apikey, int uses, boolean isActive, boolean allowRead) {
        this.id = id;
        this.apikey = apikey;
        this.uses = uses;
        this.isActive = isActive;
        this.allowRead = allowRead;
    }
}
