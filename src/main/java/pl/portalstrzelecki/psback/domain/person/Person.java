package pl.portalstrzelecki.psback.domain.person;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;

import javax.persistence.*;

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

    @ManyToOne //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_club")
    private Club club;

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

    public void resetClub() {
        this.setClub(null);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @JsonIgnore
    public Club getClub() {
        return club;
    }
}
