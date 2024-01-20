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

    @Column(name = "allowDelete", nullable = false)
    @Getter @Setter
    private boolean allowDelete;

    @Column(name = "allowUpdate", nullable = false)
    @Getter @Setter
    private boolean allowUpdate;

    @Column(name = "allowRead", nullable = false)
    @Getter @Setter
    private boolean allowRead;


    // 2. Constructors
    public Token() {}

    public Token(Long id, String apikey, int uses, boolean isActive, boolean allowDelete, boolean allowUpdate, boolean allowRead) {
        this.id = id;
        this.apikey = apikey;
        this.uses = uses;
        this.isActive = isActive;
        this.allowDelete = allowDelete;
        this.allowUpdate = allowUpdate;
        this.allowRead = allowRead;
    }
}
