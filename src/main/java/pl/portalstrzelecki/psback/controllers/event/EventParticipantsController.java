package pl.portalstrzelecki.psback.controllers.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.PersonMapper;
import pl.portalstrzelecki.psback.services.EventService;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventParticipantsController {

    private final EventService eventService;


    @GetMapping("/event/participants")
    public List<PersonDTO> getEventParticipants(@RequestParam Long id_event) {

        List<Person> eventParticipants = eventService.getEventParticipants(id_event);

        if (!eventParticipants.isEmpty()) {
            return PersonMapper.INSTANCE.PersonsToPersonDtos(eventParticipants);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/event/participants")
    public ResponseEntity<?> acceptEventParticipant(@RequestParam Long id_event, Long id_person) {

        if (id_person != null && id_event != null) {
            boolean anyParticipantAdded = eventService.acceptEventParticipant(id_person, id_event);
            if (anyParticipantAdded) {
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @DeleteMapping("/event/participants")
    public ResponseEntity<?> removeEventParticipant(@RequestParam Long id_event, Long id_person) {

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
