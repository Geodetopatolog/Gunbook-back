package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import java.util.List;
import java.util.Optional;

public interface EventService {

    void saveEvent(Event event, String rangeName);
    boolean deleteEvent(Long id);
    boolean updateEvent(Event event);
    Optional<Event> getEventById(long id);
    List<Event> getAllEvents();

    List<Person> getParticipantsRequests(Long id_event);
    boolean acceptEventParticipantRequest(Long id_event, Long id_person);
    boolean rejectEventParticipantRequest(Long id_event, Long id_person);

    boolean deleteEventParticipant(Long id_event, Long id_person);
    List<Person> getEventParticipants(Long id_event);

    Optional<ShootingRange> getEventRange(Long id_event);
    boolean addEventRange(Long id_event, Long id_range);
    boolean addEventRange(Event event, String range);
    boolean deleteEventRange(Long id_event, Long id_range);

    public boolean addEventOrganizer(Long id_event, Long id_club);
    public boolean deleteEventOrganizer(Long id_event, Long id_club);
    public List<Club> getEventOrganizers(Long id_event);


}
