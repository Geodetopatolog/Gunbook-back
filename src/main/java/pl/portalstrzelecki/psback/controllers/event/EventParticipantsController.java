package pl.portalstrzelecki.psback.controllers.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.PersonMapper;
import pl.portalstrzelecki.psback.services.EventService;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventParticipantsController {

    private final EventService eventService;


    @GetMapping("/event_participants")
    public List<PersonDTO> getClubOwners(@RequestBody Map<String, Long> json) {

        Long id_event = json.get("id_event");

        Optional<Event> optionalEvent = eventService.getEventById(id_event);

        if (optionalEvent.isPresent()) {
            List<Person> eventPatricipants = optionalEvent.get().getParticipants();
                return PersonMapper.INSTANCE.PersonsToPersonDtos(eventPatricipants);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/event_participants")
    public ResponseEntity<?> addClubOwner(@RequestBody Map<String, Long> json) {
        Long id_event = json.get("id_event");
        Long id_person = json.get("id_person");

        if (id_person != null && id_event != null) {
            boolean anyParticipantAdded = eventService.addEventParticipant(id_person, id_event);
            if (anyParticipantAdded) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/event_participants")
    public ResponseEntity<?> deleteClubOwner(@RequestBody Map<String, Long> json) {
        Long id_event = json.get("id_event");
        Long id_person = json.get("id_person");

        if (id_person != null && id_event != null) {
            boolean anyEventParticipantDeleted = eventService.deleteEventParticipant(id_person, id_event);

            if (anyEventParticipantDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }


}
