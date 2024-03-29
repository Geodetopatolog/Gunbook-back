package pl.portalstrzelecki.psback.controllers.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventRegistrationDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.EventMapper;
import pl.portalstrzelecki.psback.services.EventService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;


    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody EventRegistrationDTO eventRegistrationDTO) {
        System.out.println(eventRegistrationDTO);
        eventService.saveEvent(EventMapper.INSTANCE.EventRegistrationDtoToEvent(eventRegistrationDTO),
                eventRegistrationDTO.getRangeName(),
                eventRegistrationDTO.getId_club());
    }

    @GetMapping("/event")
    public @ResponseBody EventDTO getEventById(@RequestParam Long id_event) {

        Optional<Event> optionalEvent = eventService.getEventById(id_event);

        if (optionalEvent.isPresent()) {
            return EventMapper.INSTANCE.EventToEventDto(optionalEvent.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/event")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO) {

        if (eventDTO.notNull()) {
            Event event = EventMapper.INSTANCE.EventDtoToEvent(eventDTO);

            if (eventService.updateEvent(event)) {

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Niekompletne dane");
        }
    }

    @DeleteMapping("/event")
    public ResponseEntity<?> deleteEvent(@RequestParam Long id_event) {

        boolean anyEventRemoved = eventService.deleteEvent(id_event);

        if (anyEventRemoved) {
            return ResponseEntity.ok().build();
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/event/all")
    public @ResponseBody List<EventDTO> getAllEvents() {
        return EventMapper.INSTANCE.EventsToEventDtos(eventService.getAllEvents());
    }

}