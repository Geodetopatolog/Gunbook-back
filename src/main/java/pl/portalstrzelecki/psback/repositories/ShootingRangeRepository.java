package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import java.util.Optional;

public interface ShootingRangeRepository extends CrudRepository<ShootingRange, Long> {
    Optional<ShootingRange> findByName(String range_name);
}
