package pl.portalstrzelecki.psback.controllers.shootingrange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.services.ClubService;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShootingRangeClubController {

    private final ShootingRangeService shootingRangeService;


    @GetMapping("/range_clubs")
    public List<Club> getClubRanges(@RequestBody Map<String, Long> json) {

        Long id_club = json.get("id_range");
        Optional<ShootingRange> optionalShootingRange = shootingRangeService.getShootingRangeById(id_club);
        ShootingRange shootingRange = optionalShootingRange.get();
        return shootingRange.getClubs();
    }


    @PatchMapping("/range_clubs")
    public ResponseEntity<?> addClubRange(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_range = json.get("id_range");

        if (id_range != null && id_club != null) {
            boolean anyClubAdded = shootingRangeService.addClub(id_range, id_club);
            if (anyClubAdded) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/range_clubs")
    public ResponseEntity<?> deleteClubRange(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_range = json.get("id_range");

        if (id_range != null && id_club != null) {
            boolean anyRangeClubRemoved = shootingRangeService.deleteRangeClub(id_range, id_club);

            if (anyRangeClubRemoved) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

}
