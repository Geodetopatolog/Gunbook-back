package pl.portalstrzelecki.psback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.services.PersonService;

import javax.servlet.http.HttpServletResponse;

@RestController
@EnableAutoConfiguration
public class MyApplication {

    @Autowired
    PersonService personService;

    @RequestMapping("/")
    public String jeden(HttpServletResponse response) {
        Person person = Person.builder()
                .name("Rafał")
                .surname("Szatkowski")
                .nick("Zenek")
                .build();
        personService.savePerson(person);

        return person.toString();
    }

    @PostMapping("/person")
    public @ResponseBody Person addPerson(@RequestBody Person person) {

        personService.savePerson(person);

        return person;
    }

    @RequestMapping("/dwa")
    public Person dwa(HttpServletResponse response) {

        Person person = personService.getPersonById(6L);

        person.setDescription("Mega Ziomek");

        personService.updatePerson(6L, person);

        return person;
    }

}