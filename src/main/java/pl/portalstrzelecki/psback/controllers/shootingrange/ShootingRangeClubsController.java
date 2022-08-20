package pl.portalstrzelecki.psback.controllers.shootingrange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ClubMapper;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShootingRangeClubsController {

    private final ShootingRangeService shootingRangeService;


    @GetMapping("/range/clubs")
    public List<ClubDTO> getRangeClubs(@RequestParam Long id_range) {

        List<Club> rangeClubs = shootingRangeService.getRangeClubs(id_range);

        if (!rangeClubs.isEmpty()) {
            return ClubMapper.INSTANCE.ClubToClubDtos(rangeClubs);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }

    }


    @PatchMapping("/range/clubs")
    public ResponseEntity<?> addRangeClub(@RequestParam Long id_range, Long id_club) {

        if (id_range != null && id_club != null) {
            boolean anyClubAdded = shootingRangeService.addRangeClub(id_range, id_club);
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
    public ResponseEntity<?> deleteRangeClub(@RequestParam Long id_range, Long id_club) {

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
