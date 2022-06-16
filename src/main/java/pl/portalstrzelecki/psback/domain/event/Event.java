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
import java.util.stream.Collectors;

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

    @ManyToMany
    @JoinTable(
            name = "event_organizers",
            joinColumns = @JoinColumn(name = "id_event"),
            inverseJoinColumns = @JoinColumn(name = "id_club")
    )
    private List<Club> organizers;

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
        this.setOrganizers(null);
    }

    public void addOrganizer(Club club) {
        this.organizers.add(club);
    }

    public List<String> getOrganizersName() {
        if (organizers==null) {
            List<String> messageList = new ArrayList<>();
            messageList.add("Nie przypisano organizatorów");
            return messageList;
        } else {
            return organizers.stream().map(Club::getName).collect(Collectors.toList());
        }
    }

    public int getParticipantCount() {
        return participants.size();
    }

    public String getPlaceName() {
        if (place==null) {
            return "Nie przypisano strzelnicy";
        } else {
            return place.getName();
        }
    }





}
