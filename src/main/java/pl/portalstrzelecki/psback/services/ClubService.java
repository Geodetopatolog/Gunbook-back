package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;
import java.util.Optional;


public interface ClubService {
    void saveClub(Club club);
    boolean deleteClub(Long id);
    boolean updateClub(Long id, Club club);
    Optional<Club> getClubById(long id);
    boolean addClubMember(Long id_person, Long id_club);

    List<Club> getAllClubs();

    boolean deleteClubMember(Long id_person, Long id_club);

    List<Person> getClubMembers(Long id_club);
}
