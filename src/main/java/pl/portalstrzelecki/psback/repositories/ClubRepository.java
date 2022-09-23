package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.portalstrzelecki.psback.domain.club.Club;

import java.util.Optional;

public interface ClubRepository extends CrudRepository<Club, Long> {

//    @Query("select e from Event e where e.organizers = :queryName")
//    public List<Event> getEventsAssignedToClubWithId(@Param("queryName") Long id);

    @Query("select c from Club c where c.name = :queryName")
    Optional<Club> getClubByName(@Param("queryName") String name);

}
