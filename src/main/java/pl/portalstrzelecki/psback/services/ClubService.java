package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import java.util.List;
import java.util.Optional;


public interface ClubService {
//todo pozamieniać wszędzie kolejność zmiennycy, pierwsze ID ma być od nazwy kontrolera
    void saveClub(Club club);
    boolean deleteClub(Long id);
    boolean updateClub(Club club);
    Optional<Club> getClubById(long id);
    Optional<Club> getClubByName(String name);
    List<Club> getAllClubs();

    List<Person> getMembershipRequests(Long id_club);
    boolean acceptMembershipRequest(Long id_club, Long id_person);
    public boolean rejectMembershipRequest(Long id_club, Long id_person);

//    boolean addClubMember(Long id_person, Long id_club);
    List<Person> getClubMembers(Long id_club);
    boolean deleteClubMember(Long id_club, Long id_person);

    List<Event> getClubEvents(Long id_club);
    boolean addClubEvent(Long id_club, Long id_event);
    boolean deleteClubEvent(Long id_club, Long id_event);

    boolean addOwner(Long id_club, Long id_person);
    boolean deleteClubOwner(Long id_club, Long id_person);
    List<Person> getClubOwners(Long id_club);

    List<ShootingRange> getClubRanges(Long id_club);
    boolean addClubRange(Long id_club, Long id_range);
    boolean deleteClubRange(Long id_club, Long id_range);
}
