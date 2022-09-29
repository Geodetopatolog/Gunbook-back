package pl.portalstrzelecki.psback.controllers.shootingrange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.EventMapper;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;

@CrossOrigin(origins = "${cors.origin}", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class ShootingRangeEventsController {

    private final ShootingRangeService shootingRangeService;


    @GetMapping("/range/events")
    public List<EventDTO> getRangeEvents(@RequestParam Long id_range) {
        List<Event> rangeEvents = shootingRangeService.getRangeEvents(id_range);

//        if (!rangeEvents.isEmpty()) {
            return EventMapper.INSTANCE.EventsToEventDtos(rangeEvents);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "entity not found");
//        }
    }


    @PatchMapping("/range/event")
    public ResponseEntity<?> addRangeEvent(@RequestParam Long id_range, Long id_event) {

        if (id_range != null && id_event != null) {
            boolean anyRangeEventAdded = shootingRangeService.addRangeEvent(id_range, id_event);
            if (anyRangeEventAdded) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/range/event")
    public ResponseEntity<?> deleteRangeEvent(@RequestParam Long id_range, Long id_event) {

        if (id_range != null && id_event != null) {
            boolean anyRangeEventRemoved = shootingRangeService.deleteRangeEvent(id_range, id_event);

            if (anyRangeEventRemoved) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }


}
