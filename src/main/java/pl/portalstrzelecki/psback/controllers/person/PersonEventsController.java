package pl.portalstrzelecki.psback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.EventMapper;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PersonEventsController {

    private final PersonService personService;

    @GetMapping("/person/joined_events")
    public List<EventDTO> getEventsByPersonId(@RequestParam Long id_person) {
        List<Event> joinedEvents = personService.getJoinedEvents(id_person);

        if (!joinedEvents.isEmpty()) {
            return EventMapper.INSTANCE.EventsToEventDtos(joinedEvents);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }

    }

    @PostMapping("/person/joined_events")
    public ResponseEntity<?> eventJoiningRequest(@RequestParam Long id_person, Long id_event) {
        if (id_person != null && id_event != null) {
            boolean anyRequestSent = personService.addJoiningEventRequest(id_person, id_event);
            if (anyRequestSent) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("person/joined_events")
    public ResponseEntity<?> quitJoinedEvent(@RequestParam Long id_person, Long id_event)
    {
        if (id_person != null && id_event != null) {
            boolean anyJoinedEventQuited = personService.quitJoinedEvent(id_person, id_event);
            if (anyJoinedEventQuited) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }




}
