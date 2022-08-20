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

    //private List<String> organizersNames;

    @ManyToOne
    @JoinColumn(name = "id_shootingrange")
    private ShootingRange place;

    private String rangeName;

    @ManyToMany
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "id_event"),
            inverseJoinColumns = @JoinColumn(name = "id_person")
    )
    private List<Person> participants = new ArrayList<>();

    private int participantsCount;

    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;
    private LocalTime hourOfStart;
    private LocalTime hourOfEnd;

    private boolean membersOnly = true;
    private boolean openEntry = false;
    private boolean competition = false;
    private boolean practice = false;
    private boolean course = false;

    private Long entryFee = 0L;

    public Event() {

    }

    @Override
    public String toString() {
        return super.toString();
    }



    public String toString2() {
        return "Event{" +
                "id_event=" + id_event +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", organizers=" + organizers +
                ", place=" + place +
                ", rangeName='" + rangeName + '\'' +
                ", participants=" + participants +
                ", participantsCount=" + participantsCount +
                ", dateOfStart=" + dateOfStart +
                ", dateOfEnd=" + dateOfEnd +
                ", hourOfStart=" + hourOfStart +
                ", hourOfEnd=" + hourOfEnd +
                ", membersOnly=" + membersOnly +
                ", openEntry=" + openEntry +
                ", competition=" + competition +
                ", practice=" + practice +
                ", course=" + course +
                ", entryFee=" + entryFee +
                '}';
    }

    public Event updateEvent(Event event) {
        this.setName(event.getName());
        this.setDescription(event.getDescription());
        this.setDateOfStart(event.getDateOfStart());
        this.setDateOfEnd(event.getDateOfEnd());
        this.setHourOfStart(event.getHourOfStart());
        this.setHourOfEnd(event.getHourOfEnd());
        this.setEntryFee(event.getEntryFee());
        this.setMembersOnly(event.isMembersOnly());
        this.setOpenEntry(event.isOpenEntry());
        this.setCompetition(event.isCompetition());
        this.setPractice(event.isPractice());
        this.setCourse(event.isCourse());
        return this;
    }


    public void resetOrganizer() {
        this.setOrganizers(null);
    }

    public void addOrganizer(Club club) {
        this.organizers.add(club);
    }

    public void deleteOrganizer(Club club) {
        this.organizers.remove(club);
    }

    public void setPlace(ShootingRange shootingRange) {
        this.place = shootingRange;

        if (shootingRange!=null) {
            this.rangeName = shootingRange.getName();
        }
    }

    public void addParticipant(Person person) {
        this.participants.add(person);
        this.participantsCount = participants.size();
    }

    public void deleteParticipant(Person person) {
        this.participants.remove(person);
        this.participantsCount = participants.size();
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


}
