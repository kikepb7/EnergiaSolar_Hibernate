package dto.projectDTO;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
public class ProjectDTO {

    // 1. Attributes
    @Getter @Setter @Expose
    private String name;
    @Getter @Setter @Expose
    private String address;
    @Getter @Setter @Expose
    private int generationCapacity;
    @Getter @Setter @Expose
    private String image;

    // 2. Constructors
    public ProjectDTO() {}

    public ProjectDTO(String name, String address, int generationCapacity, String image) {
        this.name = name;
        this.address = address;
        this.generationCapacity = generationCapacity;
        this.image = image;
    }
}
