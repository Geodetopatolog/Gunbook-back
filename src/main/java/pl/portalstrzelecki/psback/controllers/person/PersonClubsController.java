package pl.portalstrzelecki.psback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ClubMapper;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PersonClubsController {

    private final PersonService personService;

    @GetMapping("/person/clubs")
    public List<ClubDTO> getJoinedClub(@RequestParam Long id_person) {

        List<Club> joinedClubs = personService.getJoinedClubs(id_person);

//        if (!joinedClubs.isEmpty()) {
            return ClubMapper.INSTANCE.ClubToClubDtos(joinedClubs);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "entity not found");
//        }
    }

    @DeleteMapping("person/clubs")
    public ResponseEntity<?> leaveJoinedClub(@RequestParam Long id_person, Long id_club)
    {
        if (id_person != null && id_club != null) {
            boolean anyClubDeleted = personService.leaveJoinedClub(id_person, id_club);
            if (anyClubDeleted) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @GetMapping("/person/clubs/ids")
    public List<Long> getJoinedClubIds(@RequestParam Long id_person) {

        List<Long> joinedClubsIds = personService.getJoinedClubsIds(id_person);

//        if (!joinedClubsIds.isEmpty()) {
            return joinedClubsIds;
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "entity not found");
//        }
    }



    @GetMapping("/person/clubs/request")
    public List<ClubDTO> getMembershipRequests(@RequestParam Long id_person) {

        List<Club> membershipRequests = personService.getJoinedClubsRequests(id_person);
//
//        if (!membershipRequests.isEmpty()) {
            return ClubMapper.INSTANCE.ClubToClubDtos(membershipRequests);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "entity not found");
//        }
    }


    @PatchMapping("/person/clubs/request")
    public ResponseEntity<?> addMembershipRequest(@RequestParam Long id_club, Long id_person) {

        if (id_person != null && id_club != null) {
            boolean anyRequestAdded = personService.addJoinedClubRequest(id_person, id_club);
            if (anyRequestAdded) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("person/clubs/request")
    public ResponseEntity<?> deleteMembershipRequest(@RequestParam Long id_person, Long id_club) {
        if (id_person != null && id_club != null) {
            boolean anyMembershipRequestCanceled = personService.cancelJoinedClubRequest(id_person, id_club);
            if (anyMembershipRequestCanceled) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @GetMapping("/person/clubs/request/ids")
    public List<Long> getAppliedClubIds(@RequestParam Long id_person) {

        List<Long> appliedClubsIds = personService.getAppliedClubsIds(id_person);

//        if (!appliedClubsIds.isEmpty()) {
            return appliedClubsIds;
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "entity not found");
//        }
    }




    @GetMapping("/person/owned_clubs")
    public List<ClubDTO> getOwnedClubs(@RequestParam Long id_person)
    {
        List<Club> ownedClubs = personService.getOwnedClubs(id_person);

        if (!ownedClubs.isEmpty()) {
            return ClubMapper.INSTANCE.ClubToClubDtos(ownedClubs);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/person/owned_clubs")
    public ResponseEntity<?> addOwnedClub(@RequestParam Long id_club, Long id_person) {

        if (id_person != null && id_club != null) {
            boolean anyOwnedClubAdded = personService.addOwnedClub(id_person, id_club);
            if (anyOwnedClubAdded) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("person/owned_clubs")
    public ResponseEntity<?> cancelClubOwnership(@RequestParam Long id_person, Long id_club)
    {
        if (id_person != null && id_club != null) {
            boolean anyClubOwnershipCanceled = personService.cancelClubOwnership(id_person, id_club);
            if (anyClubOwnershipCanceled) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @GetMapping("/person/owned_clubs/ids")
    public List<Long> getOwnedClubsIds(@RequestParam Long id_person)
    {
        List<Long> ownedClubsIds = personService.getOwnedClubsIds(id_person);

//        if (!ownedClubsIds.isEmpty()) {
            return ownedClubsIds;
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "entity not found");
//        }
    }

}
