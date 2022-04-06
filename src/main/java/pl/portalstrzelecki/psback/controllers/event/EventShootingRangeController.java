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
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventShootingRangeController {

    private final EventService eventService;


    @GetMapping("event_range")
    public ShootingRangeDTO getEventRange(@RequestBody Map<String, Long> json) {

        Long id_event = json.get("id_event");

        Optional<ShootingRange> optionalShootingRange = eventService.getPlace(id_event);

        if (optionalShootingRange.isPresent()) {
            ShootingRange shootingRange = optionalShootingRange.get();
            return ShootingRangeMapper.INSTANCE.ShootingRangeToShootingRangeDTO(shootingRange);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "nie ustawiono miejsca wydarzenia");
        }
    }

    @PatchMapping("/event_range")
    public ResponseEntity<?> addEventRange(@RequestBody Map<String, Long> json) {
        Long id_range = json.get("id_range");
        Long id_event = json.get("id_event");


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

    @DeleteMapping("/event_range")
    public ResponseEntity<?> deleteEventRange(@RequestBody Map<String, Long> json) {
        Long id_range = json.get("id_range");
        Long id_event = json.get("id_event");

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
