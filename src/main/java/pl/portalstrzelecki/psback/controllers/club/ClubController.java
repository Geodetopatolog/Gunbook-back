package pl.portalstrzelecki.psback.controllers.club;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ClubMapper;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/club")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClub(@RequestBody ClubDTO clubDTO) {

        clubService.saveClub(ClubMapper.INSTANCE.ClubDtoToClub(clubDTO));
    }

    @GetMapping("/club")
    public @ResponseBody ClubDTO getClubById(@RequestParam Long id_club) {

        Optional<Club> optionalClub = clubService.getClubById(id_club);

        if (optionalClub.isPresent()) {
            return ClubMapper.INSTANCE.ClubToClubDto(optionalClub.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono encji");
        }
    }


    @PatchMapping("/club")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateClub(@RequestBody ClubDTO clubDTO) {

        if (clubDTO.notNull()) {
            Club club = ClubMapper.INSTANCE.ClubDtoToClub(clubDTO);
            if (clubService.updateClub(club)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Niekompletne dane");
        }
    }

    @DeleteMapping("/club")
    public ResponseEntity<?> deleteClub(@RequestParam Long id_club) {

        boolean anyClubRemoved = clubService.deleteClub(id_club);

        if (anyClubRemoved) {
            return ResponseEntity.ok().build();
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/club/all")
    public @ResponseBody List<ClubDTO> getAllClubs() {
        return ClubMapper.INSTANCE.ClubToClubDtos(clubService.getAllClubs());
    }


}
