package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import java.util.List;
import java.util.Optional;

public interface ShootingRangeService {

    void saveShootingRange(ShootingRange shootingRange);
    boolean deleteShootingRange(Long id);
    boolean updateShootingRange(ShootingRange shootingRange);
    Optional<ShootingRange> getShootingRangeById(long id);
    Optional<ShootingRange> getShootingRangeByName(String name);
    List<ShootingRange> getAllShootingRanges();

    boolean addRangeClub(Long id_range, Long id_club);
    boolean deleteRangeClub(Long id_range, Long id_club);
    List<Club> getRangeClubs(Long id_range);

    boolean addRangeEvent(Long id_range, Long id_event);
    boolean deleteRangeEvent(Long id_range, Long id_event);
    List<Event> getRangeEvents(Long id_range);
}
