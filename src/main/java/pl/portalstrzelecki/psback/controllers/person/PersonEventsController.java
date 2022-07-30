package pl.portalstrzelecki.psback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.EventMapper;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PersonEventsController {

    private final PersonService personService;

    //todo dodać resztę metod

    @GetMapping("/person/joined_events")
    public List<EventDTO> getEventsByPersonId(@RequestParam Long id_person)
    {
        Optional<Person> optionalPerson = personService.getPersonById(id_person);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            return EventMapper.INSTANCE.EventsToEventDtos(person.getEventsJoined());

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "person not found");
        }
    }

}
