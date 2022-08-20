package pl.portalstrzelecki.psback.dtoandmappers.dto.person;

import lombok.Data;

@Data
public class PersonBasicDataDTO {
    private Long id_person;
    private String name;
    private String surname;
    private String nick;
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
