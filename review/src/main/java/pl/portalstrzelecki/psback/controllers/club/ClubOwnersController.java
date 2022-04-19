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
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ClubOwnersController {

    private final ClubService clubService;

    @GetMapping("/club_owners")
    public List<PersonDTO> getClubOwners(@RequestBody Map<String, Long> json) {

        Long id_club = json.get("id_club");

        List<Person> clubOwners = clubService.getClubOwners(id_club);

        if (!clubOwners.isEmpty()) {
            return PersonMapper.INSTANCE.PersonsToPersonDtos(clubOwners);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club_owners")
    public ResponseEntity<?> addClubOwner(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_person = json.get("id_person");

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

    @DeleteMapping("/club_owners")
    public ResponseEntity<?> deleteClubOwner(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_person = json.get("id_person");

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
