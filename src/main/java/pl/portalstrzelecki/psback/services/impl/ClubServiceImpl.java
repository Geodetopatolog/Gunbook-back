package pl.portalstrzelecki.psback.services.impl;

import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.repositories.ClubRepository;
import pl.portalstrzelecki.psback.repositories.EventRepository;
import pl.portalstrzelecki.psback.repositories.PersonRepository;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService {

    final
    ClubRepository clubRepository;

    final
    PersonRepository personRepository;

    final
    EventRepository eventRepository;

    public ClubServiceImpl(ClubRepository clubRepository, PersonRepository personRepository, EventRepository eventRepository) {
        this.clubRepository = clubRepository;
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void saveClub(Club club) {
        club.setId_club(null);
        clubRepository.save(club);
    }

    @Override
    public boolean deleteClub(Long id) {
        Optional<Club> optionalClub = clubRepository.findById(id);
        if(optionalClub.isPresent()) {
            Club club = optionalClub.get();
            club.getMembers().stream().forEach(person -> person.resetClub());
            club.getEvents().stream().forEach(event -> event.resetOrganizer());
            clubRepository.save(club);
            clubRepository.delete(optionalClub.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateClub(Long id, Club club) {
        Optional<Club> optionalClub = clubRepository.findById(id);
        if(optionalClub.isPresent()) {
            clubRepository.save(optionalClub.get().updateClub(club));
            return true;
        }
        else {
            return false;
        }

    }
    @Override
    public Optional<Club> getClubById(long id) {
         Optional<Club> optionalClub = clubRepository.findById(id);
        //System.out.println("znalazłem klub " + optionalClub.get());
        return optionalClub;
    }

    @Override
    public boolean addClubMember(Long id_person, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if(optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person member = optionalPerson.get();

            club.addMember(member);
            clubRepository.save(club);
            member.setClub(club);
            personRepository.save(member);
            return true;
        }
        else return false;
    }

    @Override
    public List<Club> getAllClubs() {
        return (List<Club>) clubRepository.findAll();
    }

    @Override
    public boolean deleteClubMember(Long id_person, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person member = optionalPerson.get();

            club.deleteMember(member);
            member.resetClub();
            clubRepository.save(club);
            personRepository.save(member);
            return true;
        }
        else return false;
    }

    @Override
    public List<Person> getClubMembers(Long id_club) {

        Optional<Club> optionalClub = clubRepository.findById(id_club);

        if (optionalClub.isPresent()) {
            return optionalClub.get().getMembers();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> getClubEvents(Long id_club) {

        Optional<Club> optionalClub = clubRepository.findById(id_club);

        if (optionalClub.isPresent()) {
            return optionalClub.get().getEvents();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addClubEvent(Long id_event, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalClub.isPresent() && optionalEvent.isPresent()) {
            Club club = optionalClub.get();
            Event event = optionalEvent.get();

            club.addEvent(event);
            clubRepository.save(club);
            event.setOrganizer(club);
            eventRepository.save(event);
            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteClubEvent(Long id_event, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalClub.isPresent() && optionalEvent.isPresent()) {
            Club club = optionalClub.get();
            Event event = optionalEvent.get();

            club.deleteEvent(event);
            event.resetOrganizer();
            clubRepository.save(club);
            eventRepository.save(event);
            return true;
        }
        else return false;
    }

    @Override
    public boolean addOwner(Long id_person, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person person = optionalPerson.get();

            club.addOwner(person);
            clubRepository.save(club);
            person.addOwnedClub(club);
            personRepository.save(person);
            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteClubOwner(Long id_person, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person person = optionalPerson.get();

            if (club.getOwners().contains(person)) {
                club.deleteOwner(person);
                person.deleteOwnedClub(club);

                clubRepository.save(club);
                personRepository.save(person);
                return true;
            } else return false;


        } else return false;






    }
}
