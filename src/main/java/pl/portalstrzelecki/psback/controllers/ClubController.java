package pl.portalstrzelecki.psback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.services.ClubService;


import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class ClubController {

    final
    ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping("/club")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Club createClub(@RequestBody Club club) {
        clubService.saveClub(club);
        return club;
    }

    @GetMapping("/club")
    public @ResponseBody
    Club getClubById(@RequestBody Club club) {
        Optional<Club> optionalClub = clubService.getClubById(club.getId_club());

        if (optionalClub.isPresent()) {
            return optionalClub.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateClub(@RequestBody Club club) {
        clubService.updateClub(club.getId_club(), club);
    }



    @DeleteMapping("/club")
    public ResponseEntity<?> deleteClub(@RequestBody Club club) {
        boolean anyClubRemoved = clubService.deleteClub(club.getId_club());

        if (anyClubRemoved) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/clubs")
    public @ResponseBody
    List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }




}
