package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    void savePerson(Person person, UserData userData);
    boolean deletePerson(Long id);
    boolean updatePerson(Person person);
    Optional<Person> getPersonById(long id);

    List<Person> getAllPersons();

    List<Person> getPersonWithNameEquals(String name);



    boolean leaveJoinedClub(Long id_person, Long id_club);
    List<Club> getJoinedClubs(Long id_person);

    boolean cancelClubOwnership(Long id_person, Long id_club);
    boolean addOwnedClub(Long id_person, Long id_club);
    List<Club> getOwnedClubs(Long id_person);


    boolean addJoiningEventRequest(Long id_person, Long id_event);
    boolean quitJoinedEvent(Long id_person, Long id_event);
    List<Event> getJoinedEvents(Long id_person);
}
