package pl.portalstrzelecki.psback.controllers.club;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange.ShootingRangeDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.EventMapper;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ShootingRangeMapper;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubShootingRangesController {

    private final ClubService clubService;

    @GetMapping("club/ranges")
    public List<ShootingRangeDTO> getClubRanges(@RequestParam Long id_club) {

        List<ShootingRange> clubRanges = clubService.getClubRanges(id_club);

        if (!clubRanges.isEmpty()) {
            return ShootingRangeMapper.INSTANCE.ShootingRangesToShootingRangeDtos(clubRanges);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club/ranges")
    public ResponseEntity<?> addClubRange(@RequestParam Long id_club, Long id_range) {

        if (id_range != null && id_club != null) {
            boolean anyRangeAdded = clubService.addClubRange(id_club, id_range);
            if (anyRangeAdded) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/club/ranges")
    public ResponseEntity<?> deleteClubRange(@RequestParam Long id_club, Long id_range) {

        if (id_range != null && id_club != null) {
            boolean anyRangeRemoved = clubService.deleteClubRange(id_club, id_range);
            if (anyRangeRemoved) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

}
