package pl.portalstrzelecki.psback.controllers.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ClubMapper;
import pl.portalstrzelecki.psback.services.EventService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class EventOrganizersController {

    private final EventService eventService;


    @GetMapping("event/organizers")
    public List<ClubDTO> getEventOrganizers(@RequestParam Long id_event) {

        List<Club> eventOrganizers = eventService.getEventOrganizers(id_event);

//        if (!eventOrganizers.isEmpty()) {
            return ClubMapper.INSTANCE.ClubToClubDtos(eventOrganizers);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "entity not found");
//        }

    }

    @PatchMapping("/event/organizers") //todo add czy set?
    public ResponseEntity<?> addEventOrganizer(@RequestParam Long id_event, Long id_club) {

        if (id_event != null && id_club != null) {
            boolean anyEventOrganizerAdded = eventService.addEventOrganizer(id_event, id_club);
            if (anyEventOrganizerAdded) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/event/organizers")
    public ResponseEntity<?> removeEventOrganizer(@RequestParam Long id_event, Long id_club) {

        if (id_event != null && id_club != null) {
            boolean anyEventOrganizerDeleted = eventService.deleteEventOrganizer(id_event, id_club);
            if (anyEventOrganizerDeleted) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }
}
