package pl.portalstrzelecki.psback.dtoandmappers.dto.person;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PersonRegistrationDTO {

    private Long id_person;
    private String name;
    private String surname;
    private String nick;
    private String description;
    private String email;
    private String username;
    private String password;

    public boolean notNull() {
        return !(id_person == null ||
                name == null ||
                surname == null ||
                nick == null ||
                description == null ||
                email == null ||
                username == null ||
                password == null);
    }


}
