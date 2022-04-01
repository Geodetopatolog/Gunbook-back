package pl.portalstrzelecki.psback.dto;

import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;

@Data
public class PersonDTO {
    private String name;
    private String surname;
    private String nick;
    private Club club;
    private String description;

    public static PersonDTO of(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setSurname(person.getSurname());
        personDTO.setNick(person.getNick());
        personDTO.setClub(person.getClub());
        personDTO.setDescription(person.getDescription());
        return personDTO;
    }


}
