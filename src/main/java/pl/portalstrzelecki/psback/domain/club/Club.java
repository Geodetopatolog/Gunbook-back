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
    @Column(name = "ID")
    private Long id_club;

    @Column(name = "URL_loga_klubu")
    private String logoURL = "";
    @Column(name = "Nazwa")
    private String name = "";
    @Column(name = "Opis")
    private String description = "";
    @Column(name = "Email")
    private String email; //todo władować tutaj ogółne dane kontaktowe, email, telefon, adres

    @ManyToMany(mappedBy = "ownedClubs", cascade = CascadeType.MERGE)
    private List<Person> owners = new ArrayList<>();

    @ManyToMany(mappedBy = "clubs", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Person> members = new ArrayList<>();

    @ManyToMany(mappedBy = "clubsApplications", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Person> membershipRequests = new ArrayList<>();

    @Column(name = "Ilość_członków")
    private int members_count;

    @ManyToMany (mappedBy = "clubs", cascade = CascadeType.MERGE)
    private List<ShootingRange> ranges = new ArrayList<>();

    @ManyToMany (mappedBy = "organizers", cascade = CascadeType.MERGE)
    private List<Event> events = new ArrayList<>();

    @Column(name = "Sport")
    private boolean sport = false;
    @Column(name = "Rekreacja")
    private boolean fun = false;
    @Column(name = "Kursy")
    private boolean cours = false;

    public Club() {
    }

    @Override
    public String toString() {
        return super.toString();
    }


    public String toString2() {
        return "Club{" +
                "id_club=" + id_club +
                ", logoURL='" + logoURL + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", owners=" + owners +
                ", members=" + members +
                ", membershipRequests=" + membershipRequests +
                ", members_count=" + members_count +
                ", ranges=" + ranges +
                ", events=" + events +
                ", sport=" + sport +
                ", fun=" + fun +
                ", cours=" + cours +
                '}';
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
