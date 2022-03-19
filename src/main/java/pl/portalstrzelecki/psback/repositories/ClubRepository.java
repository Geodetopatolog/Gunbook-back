package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.portalstrzelecki.psback.domain.club.Club;

public interface ClubRepository extends CrudRepository<Club, Long> {
}
