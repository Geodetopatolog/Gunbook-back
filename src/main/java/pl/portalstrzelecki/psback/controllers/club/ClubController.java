package pl.portalstrzelecki.psback.controllers.club;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.component.mailer.Mailer;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ClubMapper;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public @ResponseBody ClubDTO getClubById(@RequestBody(required = false) Map<String, Long> json,
                                             @RequestParam (name="name") Optional<String> name) {
//todo dokładnie jak ma wyglądać przekazywanie parametrów do wyszukiwania, ustali się jak będę robił frontend :)
// to tutaj to taki ładny kontroler, jak ustalę sposób przekazywania parametrów, to wszystkie ładnie pozmieniam
        Optional<Club> optionalClub;

        if (name.isPresent()) {
             optionalClub = clubService.getClubByName(name.get());
            } else if (json != null && json.containsKey("id_club")) {
            Long id_club = json.get("id_club");
            optionalClub = clubService.getClubById(id_club);
            } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Niepoprawne dane wejściowe");
        }

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
    public ResponseEntity<?> deleteClub(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");

        boolean anyClubRemoved = clubService.deleteClub(id_club);

        if (anyClubRemoved) {
            return ResponseEntity.ok().build();
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/clubs")
    public @ResponseBody
    List<ClubDTO> getAllClubs() {
        return ClubMapper.INSTANCE.ClubToClubDtos(clubService.getAllClubs());
    }


}
