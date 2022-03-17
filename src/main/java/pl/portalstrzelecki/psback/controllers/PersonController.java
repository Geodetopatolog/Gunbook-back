package pl.portalstrzelecki.psback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.services.PersonService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class PersonController {

    @Autowired
    PersonService personService;


    @PostMapping("/person")
    public @ResponseBody Person addPerson(@RequestBody Person person) {

        personService.savePerson(person);
        return person;
    }

    @GetMapping("/person")
    public @ResponseBody Person getPersonById(@RequestBody Person person)
    {
        return personService.getPersonById(person.getId());
    }

    @PatchMapping("/person")
    public @ResponseBody Person changePersonData(@RequestBody Person person) {

        personService.updatePerson(person.getId(), person);
        return person;
    }

    @DeleteMapping("/person")
    public @ResponseBody String deletePerson(@RequestBody Person person) {
        personService.deletePerson(person.getId());
        return "usunięto";
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