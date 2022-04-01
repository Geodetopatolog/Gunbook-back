package pl.portalstrzelecki.psback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dto.PersonDTO;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody PersonDTO personDTO) {
        personService.savePerson(Person.of(personDTO));
    }

    @GetMapping("/person")
    public @ResponseBody PersonDTO getPersonById(@RequestBody Person person)
    {
        Optional<Person> optionalPerson = personService.getPersonById(person.getId_person());
        if (optionalPerson.isPresent()) {
            return PersonDTO.of(optionalPerson.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/person")
    public ResponseEntity<?> updatePerson(@RequestBody Person person) {

        if (personService.updatePerson(person.getId_person(), person)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<?> deletePerson(@RequestBody Person person) {
       boolean anyPersonRemoved = personService.deletePerson(person.getId_person());

       if (anyPersonRemoved) {
           return ResponseEntity.ok().build();
       }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/persons")
    public @ResponseBody List<Person> getAllPersonNamed(@RequestParam(name="name") Optional<String> name) {

        if (name.isPresent()) {
            return personService.getPersonWithNameEquals(name.get());
        }
        else
        {
            return personService.getAllPersons();
        }
    }

}