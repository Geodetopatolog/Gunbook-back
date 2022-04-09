package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;
import java.util.Optional;


public interface ClubService {

    void saveClub(Club club);
    boolean deleteClub(Long id);
    boolean updateClub(Club club);
    Optional<Club> getClubById(long id);
    boolean addClubMember(Long id_person, Long id_club);

    List<Club> getAllClubs();

    boolean deleteClubMember(Long id_person, Long id_club);

    List<Person> getClubMembers(Long id_club);

    List<Event> getClubEvents(Long id_club);

    boolean addClubEvent(Long id_event, Long id_club);

    boolean deleteClubEvent(Long id_event, Long id_club);

    boolean addOwner(Long id_person, Long id_club);

    boolean deleteClubOwner(Long id_person, Long id_club);
    List<Person> getClubOwners(Long id_club);

    Optional<Club> getClubByName(String name);
}
