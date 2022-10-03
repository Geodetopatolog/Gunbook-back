package pl.portalstrzelecki.psback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.PersonMapper;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody PersonDTO personDTO) {
        personService.savePerson(PersonMapper.INSTANCE.PersonDtoToPerson(personDTO));
    }

    @GetMapping("/person")
    public @ResponseBody PersonDTO getPersonById(@RequestBody Map<String, Long> json)
    {
        Long id_person = json.get("id_person");
        Optional<Person> optionalPerson = personService.getPersonById(id_person);
        if (optionalPerson.isPresent()) {
            return PersonMapper.INSTANCE.PersonToPersonDto(optionalPerson.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/person")
    public ResponseEntity<?> updatePerson(@RequestBody PersonDTO personDTO) {
        if (personDTO.notNull()) {
            Person person = PersonMapper.INSTANCE.PersonDtoToPerson(personDTO);
            if (personService.updatePerson(person)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Niekompletne dane");
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<?> deletePerson(@RequestBody Map<String, Long> json) {
        Long id_person = json.get("id_person");
        boolean anyPersonRemoved = personService.deletePerson(id_person);

       if (anyPersonRemoved) {
           return ResponseEntity.ok().build();
       }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/persons")
    public @ResponseBody List<PersonDTO> getAllPersonNamed(@RequestParam(name="name") Optional<String> name) {

        if (name.isPresent()) {
            return PersonMapper.INSTANCE.PersonsToPersonDtos(personService.getPersonWithNameEquals(name.get()));
        }
        else
        {
            return PersonMapper.INSTANCE.PersonsToPersonDtos(personService.getAllPersons());
        }
    }

}