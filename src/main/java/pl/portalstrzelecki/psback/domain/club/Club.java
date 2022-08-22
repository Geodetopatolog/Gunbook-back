package pl.portalstrzelecki.psback.domain.club;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id_club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_club;

    private String logoURL = "";
    private String name = "";
    private String description = "";
    private String email; //todo władować tutaj ogółne dane kontaktowe, email, telefon, adres

    @ManyToMany(mappedBy = "ownedClubs", cascade = CascadeType.MERGE)
    private List<Person> owners;

    @ManyToMany(mappedBy = "clubs", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Person> members = new ArrayList<>();

    @ManyToMany(mappedBy = "clubsApplications", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Person> membershipRequests = new ArrayList<>();

    private int members_count;

    @ManyToMany (mappedBy = "clubs", cascade = CascadeType.MERGE)
    private List<ShootingRange> ranges = new ArrayList<>();

    @ManyToMany (mappedBy = "organizers", cascade = CascadeType.MERGE)
    private List<Event> events = new ArrayList<>();

    private boolean sport = false;
    private boolean fun = false;
    private boolean cours = false;

    public Club() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Club updateClub(Club club) {
        this.logoURL = club.getLogoURL();
        this.name = club.getName();
        this.description = club.getDescription();
        this.sport = club.isSport();
        this.fun = club.isFun();
        this.cours = club.isCours();
        return this;
    }

    public void addMember(Person member) {
        members.add(member);
        members_count = members.size();
    }

    public void deleteMember(Person person) {
        members.remove(person);
        members_count = members.size();
    }

    public void addRange(ShootingRange shootingRange) {
        ranges.add(shootingRange);
    }

    public void deleteRange(ShootingRange shootingRange) {
        ranges.remove(shootingRange);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    public void addOwner(Person person) {
        owners.add(person);
    }

    public void deleteOwner(Person person) {
        owners.remove(person);
    }


}
