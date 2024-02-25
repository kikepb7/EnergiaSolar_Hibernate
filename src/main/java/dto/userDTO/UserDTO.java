package dto.userDTO;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class UserDTO {

    // 1. Attributes
    @Getter @Setter @Expose
    private String name;
    @Getter @Setter @Expose
    private String lastName;
    @Getter @Setter @Expose
    private String email;
    @Getter @Setter @Expose
    private String image;


    // 2. Constructors
    public UserDTO() {}

    public UserDTO(String name, String lastName, String email, String image) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.image = image;
    }
}
