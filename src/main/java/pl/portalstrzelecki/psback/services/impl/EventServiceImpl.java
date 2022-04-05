package pl.portalstrzelecki.psback.services.impl;

import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.repositories.EventRepository;
import pl.portalstrzelecki.psback.repositories.ShootingRangeRepository;
import pl.portalstrzelecki.psback.services.EventService;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    final EventRepository eventRepository;

    final ShootingRangeRepository shootingRangeRepository;

    public EventServiceImpl(EventRepository eventRepository, ShootingRangeRepository shootingRangeRepository) {
        this.eventRepository = eventRepository;
        this.shootingRangeRepository = shootingRangeRepository;
    }

    @Override
    public void saveEvent(Event event) {
        event.setId_event(null);
        eventRepository.save(event);
    }

    @Override
    public boolean deleteEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if(optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
//            event.setOrganizer(null);
//            eventRepository.save(event);
            eventRepository.delete(event);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateEvent(Long id, Event event) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isPresent()) {
            eventRepository.save(optionalEvent.get()); //TODO dopisać metodę update
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Event> getEventById(long id) {

        return eventRepository.findById(id);
    }

    @Override
    public boolean addEventParticipant(Long id_person, Long id_club) {
        return false;
    }

    @Override
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public boolean deleteEventParticipant(Long id_person, Long id_club) {
        return false;
    }

    @Override
    public List<Person> getEventParticipants(Long id_club) {
        return null;
    }

    @Override
    public Optional<ShootingRange> getPlace(Long id_event) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if(optionalEvent.isPresent()) {
            Event event = optionalEvent.get();

            return Optional.ofNullable(event.getPlace());
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public boolean addEventRange(Long id_event, Long id_range) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalEvent.isPresent() && optionalShootingRange.isPresent()) {
            Event event = optionalEvent.get();
            ShootingRange shootingRange = optionalShootingRange.get();

                event.setPlace(shootingRange);
                shootingRange.addEvent(event);

                eventRepository.save(event);
                shootingRangeRepository.save(shootingRange);
                return true;

        } else return false;
    }

    @Override
    public boolean deleteEventRange(Long id_event, Long id_range) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalEvent.isPresent() && optionalShootingRange.isPresent()) {
            Event event = optionalEvent.get();
            ShootingRange shootingRange = optionalShootingRange.get();

            event.setPlace(null);
            shootingRange.deleteEvent(event);

            eventRepository.save(event);
            shootingRangeRepository.save(shootingRange);
            return true;
        } else return false;
    }




}
