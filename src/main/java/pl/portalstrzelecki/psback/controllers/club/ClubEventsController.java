package pl.portalstrzelecki.psback.controllers.club;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.EventMapper;
import pl.portalstrzelecki.psback.services.ClubService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubEventsController {

    private final ClubService clubService;

    @GetMapping("club/events_organized")
    public List<EventDTO> getClubEvents(@RequestParam Long id_club) {

        List<Event> clubEvents = clubService.getClubEvents(id_club);

        if (!clubEvents.isEmpty()) {
            return EventMapper.INSTANCE.EventsToEventDtos(clubEvents);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club/events_organized")
    public ResponseEntity<?> addClubEvents(@RequestParam Long id_club, Long id_event) {

        if (id_event != null && id_club != null) {
            boolean anyEventAdded = clubService.addClubEvent(id_event, id_club);
            if (anyEventAdded) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/club/events_organized")
    public ResponseEntity<?> deleteClubEvent(@RequestParam Long id_club, Long id_event) {

        if (id_event != null && id_club != null) {
            boolean anyEventRemoved = clubService.deleteClubEvent(id_event, id_club);
            if (anyEventRemoved) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }


}
