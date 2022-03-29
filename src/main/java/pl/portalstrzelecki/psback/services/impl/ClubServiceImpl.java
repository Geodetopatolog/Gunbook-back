package pl.portalstrzelecki.psback.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.repositories.ClubRepository;
import pl.portalstrzelecki.psback.repositories.PersonRepository;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    PersonRepository personRepository;

    @Override
    public void saveClub(Club club) {
        club.setId_club(null);
        clubRepository.save(club);
    }

    @Override
    public boolean deleteClub(Long id) {
        Optional<Club> optionalClub = clubRepository.findById(id);
        if(optionalClub.isPresent()) {
            Club club = optionalClub.get();
            club.getMembers().stream().forEach(person -> person.resetClub());
            clubRepository.save(club);
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
    @Override
    public Optional<Club> getClubById(long id) {
         Optional<Club> optionalClub = clubRepository.findById(id);
        //System.out.println("znalazłem klub " + optionalClub.get());
        return optionalClub;
    }

    @Override
    public boolean addClubMember(Long id_person, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if(optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person member = optionalPerson.get();

            club.addMember(member);
            clubRepository.save(club);
            member.setClub(club);
            personRepository.save(member);
            return true;
        }
        else return false;
    }

    @Override
    public List<Club> getAllClubs() {
        return (List<Club>) clubRepository.findAll();
    }

    @Override
    public boolean deleteClubMember(Long id_person, Long id_club) {
        Optional<Club> optionalClub = clubRepository.findById(id_club);
        Optional<Person> optionalPerson = personRepository.findById(id_person);

        if(optionalClub.isPresent() && optionalPerson.isPresent()) {
            Club club = optionalClub.get();
            Person member = optionalPerson.get();

            club.deleteMember(member);
            member.resetClub();
            clubRepository.save(club);
            personRepository.save(member);
            return true;
        }
        else return false;
    }

    @Override
    public List<Person> getClubMembers(Long id_club) {

        Optional<Club> optionalClub = clubRepository.findById(id_club);

        if (optionalClub.isPresent()) {
            return optionalClub.get().getMembers();
        } else {
            return new ArrayList<>();
        }


    }
}
