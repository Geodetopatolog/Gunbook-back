package pl.portalstrzelecki.psback.domain.person;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private String email;

    @ManyToMany
    @JoinTable(
            name = "clubs_owners",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_club")
    )
    private List<Club> ownedClubs;

    @ManyToMany (mappedBy = "participants")
    private List<Event> eventsJoined;

    @ManyToMany
    @JoinTable(
                    name = "clubs_members",
                    joinColumns = @JoinColumn(name = "id_person"),
                    inverseJoinColumns = @JoinColumn(name = "id_club")
            )
    private List<Club> clubs;


    public Person() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

//
//    public Person updatePerson(Person person) {
//        this.setName(person.getName());
//        this.setSurname(person.getSurname());
//        this.setNick(person.getNick());
//        this.setDescription(person.getDescription());
//        return this;
//    }

    public void resetClub() {
        this.setClubs(null);
    }

    public List<String> getClubsName() {
        if (clubs == null) {
            List<String> messageList = new ArrayList<>();
            messageList.add("Nie przypisano organizatorów");
            return messageList;
        } else {
            return clubs.stream().map(Club::getName).collect(Collectors.toList());
        }

    }

    public void addOwnedClub(Club club) {
        ownedClubs.add(club);
    }

    public void deleteOwnedClub(Club club) {
        ownedClubs.remove(club);
    }

    public void addClub(Club club) {
        this.clubs.add(club);
    }
}
