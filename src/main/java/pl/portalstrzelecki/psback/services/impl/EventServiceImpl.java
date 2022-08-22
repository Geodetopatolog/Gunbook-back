package pl.portalstrzelecki.psback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.repositories.ClubRepository;
import pl.portalstrzelecki.psback.repositories.EventRepository;
import pl.portalstrzelecki.psback.repositories.PersonRepository;
import pl.portalstrzelecki.psback.repositories.ShootingRangeRepository;
import pl.portalstrzelecki.psback.services.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    final EventRepository eventRepository;
    final ShootingRangeRepository shootingRangeRepository;
    final PersonRepository personRepository;
    final ClubRepository clubRepository;

//podstawowe------------------------------------------------------------
    @Override //todo dorobić od razu dodawanie klubu-organizatora jak się dorobi to we froncie
    public void saveEvent(Event event, String rangeName) {
        event.setId_event(null);

        if (rangeName!=null) {
            addEventRange(event, rangeName);
        } else {
            eventRepository.save(event);
        }
    }

    @Override
    public boolean deleteEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if(optionalEvent.isPresent()) {
            Event event = optionalEvent.get();

            eventRepository.delete(event);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateEvent(Event event) {
        Optional<Event> optionalEvent = eventRepository.findById(event.getId_event());

        if (optionalEvent.isPresent()) {
            eventRepository.save(optionalEvent.get().updateEvent(event));
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
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }


    //------EVENT - PARTICIPANTS----------------------------------------------


    @Override
    public List<Person> getParticipantsRequests(Long id_event) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalEvent.isPresent()) {
            return optionalEvent.get().getParticipantsRequests();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean acceptEventParticipantRequest(Long id_event, Long id_person) {
        if (addEventParticipant(id_event, id_person)) {
            deleteEventParticipantRequest(id_event, id_person);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteEventParticipantRequest(Long id_event, Long id_person) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalEvent.isPresent() && optionalPerson.isPresent()) {
            Event event = optionalEvent.get();
            Person candidate = optionalPerson.get();

            event.getParticipantsRequests().remove(candidate);
            candidate.getEventsRequests().remove(event);

            eventRepository.save(event);
            return true;
        } else return false;
    }

    @Override
    public boolean rejectEventParticipantRequest(Long id_event, Long id_person) {
        //miejsce na dodatkowe opcje :)
        return deleteEventParticipantRequest(id_event, id_person);
    }



    @Override
    public List<Person> getEventParticipants(Long id_event) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalEvent.isPresent()) {
            return optionalEvent.get().getParticipants();
        } else {
            return new ArrayList<>();
        }
    }

    public boolean addEventParticipant(Long id_event, Long id_person) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalEvent.isPresent() && optionalPerson.isPresent()) {
            Event event = optionalEvent.get();
            Person candidate = optionalPerson.get();

            if (event.getParticipantsRequests().contains(candidate)) {
                event.addParticipant(candidate);
                candidate.getEventsJoined().add(event);
                eventRepository.save(event);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteEventParticipant(Long id_event, Long id_person) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalEvent.isPresent() && optionalPerson.isPresent()) {
            Event event = optionalEvent.get();
            Person person = optionalPerson.get();

            event.deleteParticipant(person);
            person.getEventsJoined().remove(event);

            eventRepository.save(event);
//            personRepository.save(person);
            return true;
        } else return false;
    }


    //--------EVENT - PLACE------------------------------------------------------
    @Override
    public Optional<ShootingRange> getEventRange(Long id_event) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if(optionalEvent.isPresent()) {
            Event event = optionalEvent.get();

            return Optional.ofNullable(event.getPlace());
        }
        else {
            return Optional.empty();
        }
    }

    @Override //metoda przydatna przy tworzeniu relacji pomiędzy istniejącymi encjami
    public boolean addEventRange(Long id_event, Long id_range) {

        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalEvent.isPresent() && optionalShootingRange.isPresent()) {

            Event event = optionalEvent.get();
            ShootingRange shootingRange = optionalShootingRange.get();

            event.setPlace(shootingRange);
            shootingRange.addEvent(event);

            eventRepository.save(event);
//            shootingRangeRepository.save(shootingRange);
            return true;

        } else return false;
    }

    @Override //metoda przydatna przy dodawaniu od razu strzelnicy do tworzonego eventu
    public boolean addEventRange(Event event, String range_name) {
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findByName(range_name);

        if (optionalShootingRange.isPresent()) {
            ShootingRange shootingRange = optionalShootingRange.get();

            event.setPlace(shootingRange);
            shootingRange.addEvent(event);

            eventRepository.save(event);
//            shootingRangeRepository.save(shootingRange);
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
//            shootingRangeRepository.save(shootingRange);
            return true;
        } else return false;
    }

//------EVENT - ORGANIZERS -------------------------------------------------

    @Override
    public boolean addEventOrganizer(Long id_event, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if(optionalClub.isPresent() && optionalEvent.isPresent()) {
            Club organizer = optionalClub.get();
            Event event = optionalEvent.get();

            organizer.addEvent(event);
            event.addOrganizer(organizer);
            eventRepository.save(event);

            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteEventOrganizer(Long id_event, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalClub.isPresent() && optionalEvent.isPresent()) {
            Club organizer = optionalClub.get();
            Event event = optionalEvent.get();

            organizer.deleteEvent(event);
            event.deleteOrganizer(organizer);
            eventRepository.save(event);

            return true;
        }
        else return false;
    }

    @Override
    public List<Club> getEventOrganizers(Long id_event) {

        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalEvent.isPresent()) {
            return optionalEvent.get().getOrganizers();
        } else {
            return new ArrayList<>();
        }
    }





}
