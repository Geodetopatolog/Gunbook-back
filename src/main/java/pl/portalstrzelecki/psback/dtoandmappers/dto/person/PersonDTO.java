package pl.portalstrzelecki.psback.dtoandmappers.dto.person;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonDTO {

    private Long id_person;
    private String name;
    private String surname;
    private String nick;
    private List<String> clubs_name;
    private String description;
    private String email;

    public boolean notNull() {
        return !(id_person == null ||
        name == null ||
        surname == null ||
        nick == null ||
        description == null ||
        email == null);
    }


}
