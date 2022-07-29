package pl.portalstrzelecki.psback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ClubMapper;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PersonClubsController {

    private final PersonService personService;

    @GetMapping("/person/clubs")
    public List<ClubDTO> getClubsByPersonId(@RequestParam Long id_person)
    {
        Optional<Person> optionalPerson = personService.getPersonById(id_person);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            return ClubMapper.INSTANCE.ClubToClubDtos(person.getClubs());

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "person not found");
        }
    }

}
