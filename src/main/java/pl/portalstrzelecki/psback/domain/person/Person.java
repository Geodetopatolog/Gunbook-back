package pl.portalstrzelecki.psback.domain.person;

import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;

import javax.persistence.*;
import java.util.List;

@Data
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

    public Person(String name, String surname, String nick, String description) {
        this.name = name;
        this.surname = surname;
        this.nick = nick;
        this.description = description;
    }

//    public Person(String name, String surname, String nick, List<Club> clubs, String description) {
//        this.name = name;
//        this.surname = surname;
//        this.nick = nick;
////        this.clubs = clubs;
//        this.description = description;
//    }


    public Person(Long id, String name, String surname, String nick, String description) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.nick = nick;
        this.description = description;
    }

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
