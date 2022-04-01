package pl.portalstrzelecki.psback.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

//    private List<Person> participants;

    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;
    private LocalTime hourOfStart;
    private LocalTime hourOfEnd;

    private boolean membersOnly = true;
    private boolean openEntry = false;

    private boolean isCompetition = false;

    private boolean isPractice = false;

    private boolean isCourse = false;

    private int entryFee = 0;

    public Event() {

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void resetOrganizer() {
        this.setOrganizer(null);
    }

    public void resetPlacer() {
        this.setPlace(null);
    }

    public void addRange(ShootingRange shootingRange) {

    }

    //    public void addParticipant(Person person) {
//        participants.add(person);
//    }
//
//    public void deleteParticipant(Person person) {
//        participants.remove(person);
//    }
//
//    public void deleteParticipant(String name, String surname) {
//        for (int i=0;i<participants.size();i++) {
//            if (participants.get(i).getName().equals(name) && participants.get(i).getSurname().equals(surname)) {
//                participants.remove(i);
//            }
//        }
//    }
//    public void deleteParticipant(String nick) {
//        for (int i=0;i<participants.size();i++) {
//            if (participants.get(i).getNick().equals(nick)) {
//                participants.remove(i);
//            }
//        }
//    }




}
