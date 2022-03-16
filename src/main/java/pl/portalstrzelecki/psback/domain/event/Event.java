package pl.portalstrzelecki.psback.domain.event;

import lombok.Data;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class Event {

    private String name;
    private String description;

    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;
    private LocalTime hourOfStart;
    private LocalTime hourOfEnd;

    private boolean membersOnly = true;
    private boolean openEntry = false;
    private List<Person> participants;

    private int entryFee = 0;



    public void addParticipant(Person person) {
        participants.add(person);
    }

    public void deleteParticipant(Person person) {
        participants.remove(person);
    }

    public void deleteParticipant(String name, String surname) {
        for (int i=0;i<participants.size();i++) {
            if (participants.get(i).getName().equals(name) && participants.get(i).getSurname().equals(surname)) {
                participants.remove(i);
            }
        }
    }
    public void deleteParticipant(String nick) {
        for (int i=0;i<participants.size();i++) {
            if (participants.get(i).getNick().equals(nick)) {
                participants.remove(i);
            }
        }
    }

}
