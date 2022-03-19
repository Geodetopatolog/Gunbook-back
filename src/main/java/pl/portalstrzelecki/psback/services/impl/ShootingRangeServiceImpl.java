package pl.portalstrzelecki.psback.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.repositories.ShootingRangeRepository;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;
import java.util.Optional;

@Service
public class ShootingRangeServiceImpl implements ShootingRangeService {

    @Autowired
    ShootingRangeRepository shootingRangeRepository;

    @Override
    public void saveShootingRange(ShootingRange shootingRange) {
        shootingRange.setId(null);
        shootingRangeRepository.save(shootingRange);
    }

    @Override
    public boolean deleteShootingRange(Long id) {
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id);
        if(optionalShootingRange.isPresent()) {
            shootingRangeRepository.delete(optionalShootingRange.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void updateShootingRange(Long id, ShootingRange shootingRange) {
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id);
        if(optionalShootingRange.isPresent()) {
            shootingRangeRepository.save(optionalShootingRange.get().updateShootingRange(shootingRange));
        }
        else {
            throw new RuntimeException("Nie można znaleźć człowieka");
        }
    }

    @Override
    public Optional<ShootingRange> getShootingRangeById(long id) {

        Optional <ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id);
        return optionalShootingRange;
    }

    @Override
    public List<ShootingRange> getAllShootingRanges() {
        return null;
    }
}
