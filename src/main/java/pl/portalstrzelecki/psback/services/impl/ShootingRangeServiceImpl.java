package pl.portalstrzelecki.psback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.repositories.ClubRepository;
import pl.portalstrzelecki.psback.repositories.ShootingRangeRepository;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShootingRangeServiceImpl implements ShootingRangeService {

    final ShootingRangeRepository shootingRangeRepository;
    final ClubRepository clubRepository;


    @Override
    public void saveShootingRange(ShootingRange shootingRange) {
        shootingRange.setId_shootingrange(null);
        shootingRangeRepository.save(shootingRange);
    }

    @Override
    public boolean deleteShootingRange(Long id) {
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id);
        if(optionalShootingRange.isPresent()) {
            ShootingRange shootingRange = optionalShootingRange.get();

            shootingRange.getEvents().stream().forEach(event -> event.setPlace(null));

            shootingRangeRepository.delete(shootingRange);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateShootingRange(ShootingRange shootingRange) {
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(shootingRange.getId_shootingrange());
        if(optionalShootingRange.isPresent()) {
            shootingRangeRepository.save(optionalShootingRange.get().updateShootingRange(shootingRange));
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Optional<ShootingRange> getShootingRangeById(long id) {

        Optional <ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id);
        return optionalShootingRange;
    }

    @Override
    public List<ShootingRange> getAllShootingRanges() {
        return (List<ShootingRange>) shootingRangeRepository.findAll();
    }

    @Override
    public boolean addClub(Long id_range, Long id_club) {

        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalClub.isPresent() && optionalShootingRange.isPresent()) {
            Club club = optionalClub.get();
            ShootingRange shootingRange = optionalShootingRange.get();

            if (!club.getRanges().contains(shootingRange)) {
                club.addRange(shootingRange);
                shootingRange.addClub(club);

                clubRepository.save(club);
                shootingRangeRepository.save(shootingRange);
                return true;
            } else return false;


        } else return false;
    }

    @Override
    public boolean deleteRangeClub(Long id_range, Long id_club) {

        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalClub.isPresent() && optionalShootingRange.isPresent()) {
            Club club = optionalClub.get();
            ShootingRange shootingRange = optionalShootingRange.get();

            if (club.getRanges().contains(shootingRange)) {
                club.deleteRange(shootingRange);
                shootingRange.deleteClub(club);

                clubRepository.save(club);
                shootingRangeRepository.save(shootingRange);
                return true;
            } else return false;
        } else return false;

    }
}
