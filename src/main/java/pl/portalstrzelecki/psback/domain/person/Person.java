package pl.portalstrzelecki.psback.domain.person;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;

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

    //todo sprawdzić, czy przy usuwaniu encji. usuwają się wszystkie jej relacje
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id_person;

    @Column(name = "Imię")
    private String name = "";
    @Column(name = "Nazwisko")
    private String surname = "";
    @Column(name = "Nick")
    private String nick = "";
    @Column(name = "Opis")
    private String description = "";
    @Column(name = "Email")
    private String email;

    @OneToOne (cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn (name = "ID_danych_logowania")
    private UserData userData;

    @ManyToMany
    @JoinTable(
            name = "clubs_owners",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_club")
    )
    private List<Club> ownedClubs;

    @ManyToMany (mappedBy = "participants", cascade = CascadeType.MERGE)
    private List<Event> eventsJoined;

    @ManyToMany (mappedBy = "participantsRequests", cascade = CascadeType.MERGE)
    private List<Event> eventsRequests;

    @ManyToMany //do zapisu w tabeli clubs_members wystarczy samo zapisanie encji person, ale wtedy nie zapisuje zmian w klubie
    //więc albo ręcznie w serwisie zapisujemy też klub, albo dajemy CascadeType.MERGE
    @JoinTable(
                    name = "clubs_members",
                    joinColumns = @JoinColumn(name = "id_person"),
                    inverseJoinColumns = @JoinColumn(name = "id_club")
            )
    private List<Club> clubs;

    @ManyToMany
    @JoinTable(
            name = "clubs_applications",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_club")
    )
    private List<Club> clubsApplications;


    public Person() {
    }

    @Override
    public String toString() {
        return super.toString();
    }


    public String toString2() {
        return "Person{" +
                "id_person=" + id_person +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nick='" + nick + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", userData=" + userData +
                ", ownedClubs=" + ownedClubs +
                ", eventsJoined=" + eventsJoined +
                ", clubs=" + clubs +
                '}';
    }

    public Person updatePerson(Person person) {
        this.setName(person.getName());
        this.setSurname(person.getSurname());
        this.setNick(person.getNick());
        this.setDescription(person.getDescription());
        this.setEmail(person.getEmail());
        return this;
    }

    public void addClub(Club club) {
        this.clubs.add(club);
    }
    public void leaveClub(Club club) {
        this.clubs.remove(club);
    }
    public List<String> getClubsNames() {
        if (this.clubs == null) {
            List<String> messageList = new ArrayList<>();
            messageList.add("Nie przypisano organizatorów");
            return messageList;
        } else {
            return this.clubs.stream().map(Club::getName).collect(Collectors.toList());
        }
    }
    public List<Long> getClubsIds() {
        if (this.clubs == null) {
            return new ArrayList<>();
        } else {
            return this.clubs.stream().map(Club::getId_club).collect(Collectors.toList());
        }
    }

    public List<Long> getAppliedClubsIds() {
        if (this.clubsApplications == null) {
            return new ArrayList<>();
        } else {
            return this.clubsApplications.stream().map(Club::getId_club).collect(Collectors.toList());
        }
    }




    public void addOwnedClub(Club club) {
        this.ownedClubs.add(club);
    }
    public void deleteOwnedClub(Club club) {
        this.ownedClubs.remove(club);
    }
    public List<Long> getOwnedClubsIds() {
        if (this.ownedClubs == null) {
            return new ArrayList<>();
        } else {
            return this.ownedClubs.stream().map(Club::getId_club).collect(Collectors.toList());
        }
    }


    public List<Long> getJoinedEventsIds() {
        if (this.eventsJoined == null) {
            return new ArrayList<>();
        } else {
            return this.eventsJoined.stream().map(Event::getId_event).collect(Collectors.toList());
        }
    }

    public List<Long> getRequestEventsIds() {
        if (this.eventsRequests == null) {
            return new ArrayList<>();
        } else {
            return this.eventsRequests.stream().map(Event::getId_event).collect(Collectors.toList());
        }
    }
}
