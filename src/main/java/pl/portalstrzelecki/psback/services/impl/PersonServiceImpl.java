package pl.portalstrzelecki.psback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.repositories.PersonRepository;
import pl.portalstrzelecki.psback.services.ClubService;
import pl.portalstrzelecki.psback.services.EventService;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    final PersonRepository personRepository;
    final ClubService clubService;
    final EventService eventService;

    @Override
    public void savePerson(Person person, UserData userData) {
        person.setId_person(null);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userData.setEncryptedPassword(passwordEncoder.encode(userData.getPassword()));

        userData.setPerson(person);
        person.setUserData(userData);
        personRepository.save(person);
    }

    @Override
    public boolean deletePerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if(optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            person.getEventsJoined().forEach(event -> event.getParticipants().remove(person));

            personRepository.delete(person);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updatePerson(Person person) {
        Optional<Person> optionalPerson = personRepository.findById(person.getId_person());

        if(optionalPerson.isPresent()) {
            personRepository.save(optionalPerson.get().updatePerson(person));
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Optional<Person> getPersonById(long id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }


    @Override
    public List<Person> getPersonWithNameEquals(String name) {
        return personRepository.getPersonWithNameEquals(name);
    }

    //---------------PERSON - CLUBS ---------------------------------

    //nie dodaję metody joinClub, bo jest to relacja zależna od klubu, nie od członka


    @Override
    public List<Club> getJoinedClubs(Long id_person) {
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalPerson.isPresent()) {
            return optionalPerson.get().getClubs();

        } else {
            return new ArrayList<>();
        }
    }

    @Override //funkcja stworzona dla porządku, pewnie ją kiedyś wywalę
    public boolean leaveJoinedClub(Long id_person, Long id_club) {
        return clubService.deleteClubMember(id_person, id_club);
    }

    @Override
    public boolean addOwnedClub(Long id_person, Long id_club) {
        return clubService.addOwner(id_person, id_club);
    }

    @Override
    public boolean cancelClubOwnership(Long id_person, Long id_club) {
        return clubService.deleteClubOwner(id_person, id_club);
    }

    @Override
    public List<Club> getOwnedClubs(Long id_person) {
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalPerson.isPresent()) {
            return optionalPerson.get().getOwnedClubs();

        } else {
            return new ArrayList<>();
        }
    }

    //-------------PERSON - EVENTS -----------------------------------


    @Override
    public boolean addJoiningEventRequest(Long id_person, Long id_event) {
        //todo przerobić to na prośbę o dołączenie
        Optional<Event> optionalEvent = eventService.getEventById(id_event);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if(optionalEvent.isPresent() && optionalPerson.isPresent()) {
            Event event = optionalEvent.get();
            Person participant = optionalPerson.get();

            event.addParticipant(participant);
            participant.getEventsJoined().add(event);
            personRepository.save(participant);

            //clubMail.memberRequestMail(club, member);

            return true;
        }
        else return false;
    }

    //todo dorobić metode do usuwania prośby o dołączenie
    public boolean quitJoinedEvent(Long id_person, Long id_event) {
        Optional<Event> optionalEvent = eventService.getEventById(id_event);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalEvent.isPresent() && optionalPerson.isPresent()) {
            Event event = optionalEvent.get();
            Person person = optionalPerson.get();

            event.deleteParticipant(person);
            person.getEventsJoined().remove(event);
            personRepository.save(person);
            return true;
        } else return false;
    }

    @Override
    public List<Event> getJoinedEvents(Long id_person) {
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalPerson.isPresent()) {
            return optionalPerson.get().getEventsJoined();

        } else {
            return new ArrayList<>();
        }
    }
}
