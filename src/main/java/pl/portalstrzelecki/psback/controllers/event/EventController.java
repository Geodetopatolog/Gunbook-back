package pl.portalstrzelecki.psback.controllers.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.services.EventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;


    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
    }

    @GetMapping("/event")
    public @ResponseBody
    Event getEventById(@RequestBody Event event) {
        Optional<Event> optionalEvent = eventService.getEventById(event.getId_event());

        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/event")
    public ResponseEntity<?> updateEvent(@RequestBody Event event) {
        if (eventService.updateEvent(event.getId_event(), event)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/event")
    public ResponseEntity<?> deleteEvent(@RequestBody Event event) {
        boolean anyEventRemoved = eventService.deleteEvent(event.getId_event());

        if (anyEventRemoved) {
            return ResponseEntity.ok().build();
        } else  {
            return ResponseEntity.notFound().build();
        }


    }

    @GetMapping("/events")
    public @ResponseBody
    List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

}