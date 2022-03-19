package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import java.util.List;
import java.util.Optional;

public interface ShootingRangeService {

    void saveShootingRange(ShootingRange shootingRange);
    boolean deleteShootingRange(Long id);
    void updateShootingRange(Long id, ShootingRange shootingRange);
    Optional<ShootingRange> getShootingRangeById(long id);

    List<ShootingRange> getAllShootingRanges();
}
