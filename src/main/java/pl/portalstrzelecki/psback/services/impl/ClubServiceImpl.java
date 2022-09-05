package pl.portalstrzelecki.psback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.component.mailer.clubmail.ClubMail;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.repositories.ClubRepository;
import pl.portalstrzelecki.psback.repositories.EventRepository;
import pl.portalstrzelecki.psback.repositories.PersonRepository;
import pl.portalstrzelecki.psback.repositories.ShootingRangeRepository;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    final ClubRepository clubRepository;
    final PersonRepository personRepository;
    final EventRepository eventRepository;
    final ShootingRangeRepository shootingRangeRepository;
    final ClubMail clubMail;


    @Override
    public void saveClub(Club club) {
        club.setId_club(null);
        clubRepository.save(club);
    }

    @Override
    public boolean saveClub(Club club, Long id_founder) {
        Optional<Person> optionalPerson = personRepository.findById(id_founder);

        if (optionalPerson.isPresent()) {
            Person founder = optionalPerson.get();

            club.addOwner(founder);
            club.addMember(founder);

            founder.getOwnedClubs().add(club);
            founder.getClubs().add(club);
            clubRepository.save(club);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteClub(Long id) {
        Optional<Club> optionalClub = clubRepository.findById(id);
        if(optionalClub.isPresent()) {
            Club club = optionalClub.get();
            //jeśli w polu danej klasy mamy MappedBy to trzeba usuwać po stronie obiektu konkretny obiekt w tabeli
            club.getMembers().forEach(person -> person.getClubs().remove(club));
            club.getEvents().forEach(event -> event.getOrganizers().remove(club));
            club.getRanges().forEach(shootingRange -> shootingRange.getClubs().remove(club));
            club.getOwners().forEach(person -> person.getOwnedClubs().remove(club));

            clubRepository.delete(club);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateClub(Club club) {
        Optional<Club> optionalClub = clubRepository.findById(club.getId_club());
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
        return clubRepository.findById(id);
    }

    @Override
    public Optional<Club> getClubByName(String name) {
        return clubRepository.getClubByName(name);
    }

    @Override
    public List<Club> getAllClubs() {
        return (List<Club>) clubRepository.findAll();
    }

//--------CLUB - MEMBERSHIP REQUESTS ---------------------


    @Override
    public List<Person> getMembershipRequests(Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);

        if (optionalClub.isPresent()) {
            return optionalClub.get().getMembershipRequests();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean acceptMembershipRequest(Long id_club, Long id_person) {
        if (addClubMember(id_club, id_person)) {
            deleteMembershipRequest(id_person, id_club);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteMembershipRequest(Long id_club, Long id_person) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person candidate = optionalPerson.get();

            club.getMembershipRequests().remove(candidate);
            candidate.getClubsApplications().remove(club);
            clubRepository.save(club);
            return true;
        } else return false;
    }

    @Override
    public boolean rejectMembershipRequest(Long id_club, Long id_person) {
        //miejce na dodatkowe czynności, np poinformowanie kandydata itp
        return deleteMembershipRequest(id_club, id_person);
    }






    //--------CLUB - MEMBERS ------------------------------------
//    @Override
    public boolean addClubMember(Long id_club, Long id_person) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if(optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person member = optionalPerson.get();

            if (club.getMembershipRequests().contains(member)) {
                club.addMember(member);
                member.addClub(club);
                clubRepository.save(club);
                return true;
            } else {
                return true;
            }

        } else {
            return false;
        }

    }

    @Override
    public boolean deleteClubMember(Long id_club, Long id_person) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person member = optionalPerson.get();

            club.deleteMember(member);
            member.leaveClub(club);
            clubRepository.save(club);

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


    //--------CLUB - EVENTS ------------------------------------
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
    public boolean addClubEvent(Long id_club, Long id_event) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalClub.isPresent() && optionalEvent.isPresent()) {
            Club club = optionalClub.get();
            Event event = optionalEvent.get();

            club.addEvent(event);
            event.addOrganizer(club);

            clubRepository.save(club);
            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteClubEvent(Long id_club, Long id_event) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Event> optionalEvent = eventRepository.findById(id_event);

        if (optionalClub.isPresent() && optionalEvent.isPresent()) {
            Club club = optionalClub.get();
            Event event = optionalEvent.get();

            club.deleteEvent(event);
            event.resetOrganizer();
            clubRepository.save(club);

            return true;
        }
        else return false;
    }


    //--------CLUB - OWNERS ------------------------------------
    @Override
    public boolean addOwner(Long id_club, Long id_person) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person person = optionalPerson.get();

            club.addOwner(person);
            person.addOwnedClub(club);

            clubRepository.save(club);
            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteClubOwner(Long id_club, Long id_person) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if (optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person person = optionalPerson.get();

            if (club.getOwners().contains(person)) {
                club.deleteOwner(person);
                person.deleteOwnedClub(club);

                clubRepository.save(club);

                return true;
            } else return false;

        } else return false;
    }

    @Override
    public List<Person> getClubOwners(Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);

        if (optionalClub.isPresent()) {
            return optionalClub.get().getOwners();
        } else {
            return new ArrayList<>();
        }
    }

    //---------CLUB - SHOOTING RANGE --------------------


    @Override
    public List<ShootingRange> getClubRanges(Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);

        if (optionalClub.isPresent()) {
            return optionalClub.get().getRanges();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addClubRange(Long id_club, Long id_range) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalClub.isPresent() && optionalShootingRange.isPresent()) {
            Club club = optionalClub.get();
            ShootingRange range = optionalShootingRange.get();

            club.getRanges().add(range);
            range.getClubs().add(club);

            clubRepository.save(club);
            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteClubRange(Long id_club, Long id_range) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalClub.isPresent() && optionalShootingRange.isPresent()) {
            Club club = optionalClub.get();
            ShootingRange range = optionalShootingRange.get();

            club.getRanges().remove(range);
            range.getClubs().remove(club);

            clubRepository.save(club);
            return true;
        }
        else return false;
    }
}
