package pl.portalstrzelecki.psback.dtoandmappers.dto.club;

import lombok.Data;

@Data
public class ClubRegistrationDTO {

    private Long id_club;
    private Long id_person;
    private String logoURL;
    private String name;
    private String description;
    private int members_count;
    private boolean sport;
    private boolean fun;
    private boolean cours;
    private String email;

    public boolean notNull() {
        //booleany przy braku ich podania w dto maja wartosc false
        return !(
                id_club == null ||
                        id_person == null ||
                        logoURL == null ||
                        name == null ||
                        description == null ||
                        email == null
        );
    }



}
