package pl.portalstrzelecki.psback.domain.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name = "";
    private String surname = "";
    private String nick = "";
//    private List<Club> clubs;
    private String description = "";


    public Person() {
    }

    public Person updatePerson(Person person) {
        this.setName(person.getName());
        this.setSurname(person.getSurname());
        this.setNick(person.getNick());
        this.setDescription(person.getDescription());
        return this;
    }
}
