package pl.portalstrzelecki.psback.controllers.club;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.PersonMapper;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubPeopleController {

    private final ClubService clubService;

    @GetMapping("club/members")
    public List<PersonDTO> getClubMembers(@RequestParam Long id_club) {

        List<Person> clubMembers = clubService.getClubMembers(id_club);

        if (!clubMembers.isEmpty()) {
            return PersonMapper.INSTANCE.PersonsToPersonDtos(clubMembers);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club/members")
    public ResponseEntity<?> addMember(@RequestParam Long id_club, Long id_person) {

        if (id_person != null && id_club != null) {
            boolean anyMemberAdded = clubService.addClubMember(id_person, id_club);
            if (anyMemberAdded) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/club/members")
    public ResponseEntity<?> deleteClubMember(@RequestParam Long id_club, Long id_person) {

        if (id_person != null && id_club != null) {
            boolean anyMemberRemoved = clubService.deleteClubMember(id_person, id_club);
            if (anyMemberRemoved) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }




    @GetMapping("/club/owners")
    public List<PersonDTO> getClubOwners(@RequestParam Long id_club) {

        List<Person> clubOwners = clubService.getClubOwners(id_club);

        if (!clubOwners.isEmpty()) {
            return PersonMapper.INSTANCE.PersonsToPersonDtos(clubOwners);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club/owners")
    public ResponseEntity<?> addClubOwner(@RequestParam Long id_club, Long id_person) {

        if (id_person != null && id_club != null) {
            boolean anyOwnerAdded = clubService.addOwner(id_person, id_club);
            if (anyOwnerAdded) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/club/owners")
    public ResponseEntity<?> deleteClubOwner(@RequestParam Long id_club, Long id_person) {

        if (id_person != null && id_club != null) {
            boolean anyClubOwnerDelete = clubService.deleteClubOwner(id_person, id_club);

            if (anyClubOwnerDelete) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }



}
