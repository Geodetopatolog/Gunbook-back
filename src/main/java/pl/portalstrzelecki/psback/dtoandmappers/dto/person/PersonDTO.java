package pl.portalstrzelecki.psback.dtoandmappers.dto.person;

import lombok.Data;

@Data
public class PersonDTO {

    private Long id_person;
    private String name;
    private String surname;
    private String nick;
    private String club_name;
    private String description;

    public boolean notNull() {
        if (id_person == null ||
        name == null ||
        surname == null ||
        nick == null ||
        description == null) {
            return false;
        } else {
            return true;
        }
    }


}