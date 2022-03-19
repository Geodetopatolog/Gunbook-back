package pl.portalstrzelecki.psback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class PersonController {

    @Autowired
    PersonService personService;


    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Person addPerson(@RequestBody Person person) {

        personService.savePerson(person);
        return person;
    }

    @GetMapping("/person")
    public @ResponseBody Person getPersonById(@RequestBody Person person)
    {
        Optional<Person> optionalPerson = personService.getPersonById(person.getId());
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }

    }

    @PatchMapping("/person")
    public @ResponseBody Person updatePerson(@RequestBody Person person) {

        personService.updatePerson(person.getId(), person);
        return person;
    }

    @DeleteMapping("/person")
    public ResponseEntity<?> deletePerson(@RequestBody Person person) {
       boolean anyPersonRemoved = personService.deletePerson(person.getId());

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