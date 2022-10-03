package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

public interface ShootingRangeRepository extends CrudRepository<ShootingRange, Long> {
}
