package pl.portalstrzelecki.psback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.services.ClubService;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class ClubMembersController {

    final
    ClubService clubService;

    final
    PersonService personService;

    public ClubMembersController(ClubService clubService, PersonService personService) {
        this.clubService = clubService;
        this.personService = personService;
    }


    @GetMapping("club_members")
    public List<Person> getClubMembers(@RequestBody Map<String, Long> json) {

        Long id_club = json.get("id_club");

        List<Person> clubMembers = clubService.getClubMembers(id_club);

        if (!clubMembers.isEmpty()) {
            return clubMembers;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club_members")
    public ResponseEntity<?> addMember(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_person = json.get("id_person");

        boolean anyMemberAdded = clubService.addClubMember(id_person, id_club);
        if (id_person != null && id_club != null) {
            if (anyMemberAdded) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/club_members")
    public ResponseEntity<?> deleteClubMember(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_person = json.get("id_person");

        boolean anyMemberRemoved = clubService.deleteClubMember(id_person, id_club);
        if (id_person != null && id_club != null) {
            if (anyMemberRemoved) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }






}
