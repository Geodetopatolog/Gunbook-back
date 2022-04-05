package pl.portalstrzelecki.psback.domain.person;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id_person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_person;

    private String name = "";
    private String surname = "";
    private String nick = "";
    private String description = "";

    @ManyToMany
    @JoinTable(
            name = "clubs_owners",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_club")
    )
    private List<Club> ownedClubs;

    @ManyToOne
    @JoinColumn(name = "id_club")
    private Club club;


    public Person() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static Person of(PersonDTO personDTO) {
        Person person = new Person();
        person.setId_person(personDTO.getId_person());
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setNick(personDTO.getNick());
//        person.setClub(personDTO.getClub()); //w tę strone to raczej nie będzie potrzebne :)
        person.setDescription(personDTO.getDescription());
        return person;
    }

    public Person updatePerson(Person person) {
        this.setName(person.getName());
        this.setSurname(person.getSurname());
        this.setNick(person.getNick());
        this.setDescription(person.getDescription());
        return this;
    }

    public void resetClub() {
        this.setClub(null);
    }

    public String getClub_name() {
        if (club != null) {
            return club.getName();
        } else {
            return "Nie jest człokiem żadnego klubu";
        }

    }

    @JsonIgnore
    public Club getClub() {
        return club;
    }

    public void addOwnedClub(Club club) {
        ownedClubs.add(club);
    }

    public void deleteOwnedClub(Club club) {
        ownedClubs.remove(club);
    }
}
