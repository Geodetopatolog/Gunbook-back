package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import java.util.List;
import java.util.Optional;

public interface EventService {

    void saveEvent(Event event);
    boolean deleteEvent(Long id);
    boolean updateEvent(Long id, Event event);
    Optional<Event> getEventById(long id);
    List<Event> getAllEvents();

    boolean addEventParticipant(Long id_person, Long id_event);
    boolean deleteEventParticipant(Long id_person, Long id_event);
    List<Person> getEventParticipants(Long id_event);

    Optional<ShootingRange> getPlace(Long id_event);

    boolean addEventRange(Long id_event, Long id_range);

    boolean deleteEventRange(Long id_event, Long id_range);
}
