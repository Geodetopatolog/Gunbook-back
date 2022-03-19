package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;
import java.util.Optional;

public interface ClubService {
    void saveClub(Club club);
    boolean deleteClub(Long id);
    void updateClub(Long id, Club club);
    Optional<Club> getClubById(long id);

    List<Club> getAllClubs();

}
