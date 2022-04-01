package pl.portalstrzelecki.psback.controllers.club;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.services.ClubService;
import pl.portalstrzelecki.psback.services.EventService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ClubEventsController {

    private final ClubService clubService;


    @GetMapping("club_events")
    public List<Event> getClubEvents(@RequestBody Map<String, Long> json) {

        Long id_club = json.get("id_club");

        List<Event> clubEvents = clubService.getClubEvents(id_club);

        if (!clubEvents.isEmpty()) {
            return clubEvents;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/club_events")
    public ResponseEntity<?> addClubEvents(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_event = json.get("id_event");

        boolean anyEventAdded = clubService.addClubEvent(id_event, id_club);
        if (id_event != null && id_club != null) {
            if (anyEventAdded) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/club_events")
    public ResponseEntity<?> deleteClubEvent(@RequestBody Map<String, Long> json) {
        Long id_club = json.get("id_club");
        Long id_event = json.get("id_event");

        boolean anyEventRemoved = clubService.deleteClubEvent(id_event, id_club);
        if (id_event != null && id_club != null) {
            if (anyEventRemoved) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }


}