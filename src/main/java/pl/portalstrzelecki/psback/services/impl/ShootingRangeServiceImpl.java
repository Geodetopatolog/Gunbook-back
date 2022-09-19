package pl.portalstrzelecki.psback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.repositories.ClubRepository;
import pl.portalstrzelecki.psback.repositories.EventRepository;
import pl.portalstrzelecki.psback.repositories.ShootingRangeRepository;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShootingRangeServiceImpl implements ShootingRangeService {

    final ShootingRangeRepository shootingRangeRepository;
    final ClubRepository clubRepository;
    final EventRepository eventRepository;


    @Override
    public void saveShootingRange(ShootingRange shootingRange) {
        shootingRange.setId_shootingrange(null);
        shootingRangeRepository.save(shootingRange);
    }

    @Override
    public boolean saveShootingRange(ShootingRange shootingRange, Long id_club) {

        Optional<Club> optionalClub = clubRepository.findById(id_club);

        if (optionalClub.isPresent()) {
            Club club = optionalClub.get();

            shootingRange.addClub(club);
            club.addRange(shootingRange);

            shootingRangeRepository.save(shootingRange);
            return true;
        } else {
            return false;
        }




    }

    @Override
    public boolean deleteShootingRange(Long id) {
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id);
        if(optionalShootingRange.isPresent()) {
            ShootingRange shootingRange = optionalShootingRange.get();

            shootingRange.getEvents().forEach(event -> event.setPlace(null));

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

        return shootingRangeRepository.findById(id);
    }

    @Override
    public Optional<ShootingRange> getShootingRangeByName(String name) {
        return shootingRangeRepository.findByName(name);
    }

    @Override
    public List<ShootingRange> getAllShootingRanges() {
        return (List<ShootingRange>) shootingRangeRepository.findAll();
    }



    //-----------RANGE - CLUBS -------------------------------------------
    @Override
    public boolean addRangeClub(Long id_range, Long id_club) {

        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalClub.isPresent() && optionalShootingRange.isPresent()) {
            Club club = optionalClub.get();
            ShootingRange shootingRange = optionalShootingRange.get();

            if (!club.getRanges().contains(shootingRange)) { //todo rozważyć czy robić tak w metodach, czy zmienić z list na set
                club.addRange(shootingRange);
                shootingRange.addClub(club);

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

//                clubRepository.save(club);
                shootingRangeRepository.save(shootingRange);
                return true;
            } else return false;
        } else return false;

    }

    @Override
    public List<Club> getRangeClubs(Long id_range) {
        Optional<ShootingRange> optionalRange = shootingRangeRepository.findById(id_range);

        if (optionalRange.isPresent()) {
            return optionalRange.get().getClubs();
        } else {
            return new ArrayList<>();
        }
    }
    //----------- RANGE - EVENTS -----------------------------------------


    @Override
    public boolean addRangeEvent(Long id_range, Long id_event) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalEvent.isPresent() && optionalShootingRange.isPresent()) {
            Event event = optionalEvent.get();
            ShootingRange shootingRange = optionalShootingRange.get();

                event.setPlace(shootingRange);
                shootingRange.addEvent(event);

                shootingRangeRepository.save(shootingRange);
                return true;

        } else return false;
    }

    @Override
    public boolean deleteRangeEvent(Long id_range, Long id_event) {
        Optional<Event> optionalEvent = eventRepository.findById(id_event);
        Optional<ShootingRange> optionalShootingRange = shootingRangeRepository.findById(id_range);

        if (optionalEvent.isPresent() && optionalShootingRange.isPresent()) {
            Event event = optionalEvent.get();
            ShootingRange shootingRange = optionalShootingRange.get();

            event.setPlace(null); //todo zamienić nula na coś innego, może pustą encję o nazwie "brak przypisanego miejsca"
            shootingRange.getEvents().remove(event);

            shootingRangeRepository.save(shootingRange);
            return true;

        } else return false;
    }

    @Override
    public List<Event> getRangeEvents(Long id_range) {
        Optional<ShootingRange> optionalRange = shootingRangeRepository.findById(id_range);

        if (optionalRange.isPresent()) {
            return optionalRange.get().getEvents();
        } else {
            return new ArrayList<>();
        }
    }


}
