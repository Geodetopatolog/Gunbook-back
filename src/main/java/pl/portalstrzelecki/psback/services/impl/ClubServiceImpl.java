package pl.portalstrzelecki.psback.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.repositories.ClubRepository;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    ClubRepository clubRepository;

    @Override
    public void saveClub(Club club) {
        club.setId(null);
        clubRepository.save(club);
    }

    @Override
    public boolean deleteClub(Long id) {
        Optional<Club> optionalClub = clubRepository.findById(id);
        if(optionalClub.isPresent()) {
            clubRepository.delete(optionalClub.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void updateClub(Long id, Club club) {
        Optional<Club> optionalClub = clubRepository.findById(id);
        if(optionalClub.isPresent()) {
            clubRepository.save(optionalClub.get().updateClub(club));
        }
        else {
            throw new RuntimeException("Nie można znaleźć człowieka");
        }
    }

//    @Override
//    public Club getClubById(long id) {
//        Optional<Club> optionalClub = clubRepository.findById(id);
//        if (optionalClub.isPresent()) {
//            return optionalClub.get();
//        } else {
//            throw new RuntimeException("Nie można znaleźć człowieka");
//        }
//    }
    @Override
    public Optional<Club> getClubById(long id) {
         Optional<Club> optionalClub = clubRepository.findById(id);
        return optionalClub;
    }

    @Override
    public List<Club> getAllClubs() {
        return (List<Club>) clubRepository.findAll();
    }
}
