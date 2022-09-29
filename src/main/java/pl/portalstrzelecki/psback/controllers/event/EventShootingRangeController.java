package pl.portalstrzelecki.psback.controllers.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange.ShootingRangeDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ShootingRangeMapper;
import pl.portalstrzelecki.psback.services.EventService;

import java.util.Optional;

@CrossOrigin(origins = "${cors.origin}", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class EventShootingRangeController {

    private final EventService eventService;


    @GetMapping("event/range")
    public ShootingRangeDTO getEventRange(@RequestParam Long id_event) {

        Optional<ShootingRange> optionalShootingRange = eventService.getEventRange(id_event);

        if (optionalShootingRange.isPresent()) {
            return ShootingRangeMapper.INSTANCE.ShootingRangeToShootingRangeDTO(optionalShootingRange.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "nie ustawiono miejsca wydarzenia");
        }


    }

    @PatchMapping("/event/range")
    public ResponseEntity<?> addEventRange(@RequestParam Long id_range, Long id_event) {

        if (id_event != null && id_range != null) {
            boolean anyRangeAdded = eventService.addEventRange(id_event, id_range);
            if (anyRangeAdded) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/event/range")
    public ResponseEntity<?> deleteEventRange(@RequestParam Long id_range, Long id_event) {

        if (id_event != null && id_range != null) {
            boolean anyEventRangeDeleted = eventService.deleteEventRange(id_event, id_range);
            if (anyEventRangeDeleted) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

}
