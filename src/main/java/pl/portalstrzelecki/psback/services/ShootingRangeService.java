package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import java.util.List;
import java.util.Optional;

public interface ShootingRangeService {

    void saveShootingRange(ShootingRange shootingRange);
    boolean deleteShootingRange(Long id);
    boolean updateShootingRange(ShootingRange shootingRange);
    Optional<ShootingRange> getShootingRangeById(long id);

    List<ShootingRange> getAllShootingRanges();

    boolean addClub(Long id_range, Long id_club);

    boolean deleteRangeClub(Long id_range, Long id_club);

    Optional<ShootingRange> getShootingRangeByName(String name);
}
