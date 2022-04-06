package pl.portalstrzelecki.psback.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id_event;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_club")
    private Club organizer;

    @ManyToOne
    @JoinColumn(name = "id_shootingrange")
    private ShootingRange place;

    @ManyToMany
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "id_event"),
            inverseJoinColumns = @JoinColumn(name = "id_person")
    )
    private List<Person> participants = new ArrayList<>();

    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;
    private LocalTime hourOfStart;
    private LocalTime hourOfEnd;

    private boolean membersOnly = true;
    private boolean openEntry = false;
    private boolean isCompetition = false;
    private boolean isPractice = false;
    private boolean isCourse = false;

    private Long entryFee = 0L;

    public Event() {

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void resetOrganizer() {
        this.setOrganizer(null);
    }

    public void resetPlace() {
        this.setPlace(null);
    }

    public void addRange(ShootingRange shootingRange) {

    }





}
