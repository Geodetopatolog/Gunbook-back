package pl.portalstrzelecki.psback.controllers.shootingrange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ClubMapper;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShootingRangeClubsController {

    private final ShootingRangeService shootingRangeService;


    @GetMapping("/range/clubs")
    public List<ClubDTO> getClubRanges(@RequestParam Long id_range) {

        Optional<ShootingRange> optionalShootingRange = shootingRangeService.getShootingRangeById(id_range);
        ShootingRange shootingRange = optionalShootingRange.get();
        return ClubMapper.INSTANCE.ClubToClubDtos(shootingRange.getClubs());
    }


    @PatchMapping("/range/clubs")
    public ResponseEntity<?> addClubRange(@RequestParam Long id_club, Long id_range) {

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

    @DeleteMapping("/range/clubs")
    public ResponseEntity<?> deleteClubRange(@RequestParam Long id_club, Long id_range) {

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