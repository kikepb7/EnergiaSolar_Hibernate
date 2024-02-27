package entidades;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Report")
@ToString
@EqualsAndHashCode
public class Report implements Serializable {

    // 1. Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Expose
    private Long id;

    @Column(name = "serial_report", nullable = false)
    @Getter @Setter @Expose
    private String serialReport;

    @Column(name = "registration_date")
    @Getter @Setter @Expose
    private LocalDate registrationDate;

    @Column(name = "content", nullable = false)
    @Getter @Setter @Expose
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_report_user"))
    @Getter @Setter
    private User user;


    // 2. Constructors
    public Report() {}

    public Report(Long id, String serialReport, LocalDate registrationDate, String content, User user) {
        this.id = id;
        this.serialReport = serialReport;
        this.registrationDate = registrationDate;
        this.content = content;
        this.user = user;
    }
}
