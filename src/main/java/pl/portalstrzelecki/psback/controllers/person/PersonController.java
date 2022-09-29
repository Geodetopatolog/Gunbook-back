package pl.portalstrzelecki.psback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationResponse;
import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonBasicDataDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonRegistrationDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.PersonMapper;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.PersonRegistrationMapper;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "${cors.origin}", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody PersonRegistrationDTO personRegistrationDTO) {

        personService.savePerson(PersonRegistrationMapper.INSTANCE.PersonRegistrationDTOToPerson(personRegistrationDTO)
                ,PersonRegistrationMapper.INSTANCE.PersonRegistrationDTOToUserData(personRegistrationDTO));
    }

    @GetMapping("/person")
    public @ResponseBody PersonDTO getPersonById(@RequestParam Long id_person)
    {
        Optional<Person> optionalPerson = personService.getPersonById(id_person);
        if (optionalPerson.isPresent()) {
            return PersonMapper.INSTANCE.PersonToPersonDto(optionalPerson.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/person")
    public ResponseEntity<?> updatePerson(@RequestBody PersonDTO personDTO, @RequestHeader (name="Authorization") String token) {

//        boolean permission = personService.isPermited(token.substring(7), personDTO.getId_person());


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
    public ResponseEntity<?> deletePerson(@RequestParam Long id_person) {

        boolean anyPersonRemoved = personService.deletePerson(id_person);

       if (anyPersonRemoved) {
           return ResponseEntity.ok().build();
       }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/person/all")
    public @ResponseBody List<PersonDTO> getAllPerson() {

            return PersonMapper.INSTANCE.PersonsToPersonDtos(personService.getAllPersons());
    }

    @GetMapping("/person/all/basic")
    public @ResponseBody List<PersonBasicDataDTO> getAllPersonBasicData() {

            return PersonMapper.INSTANCE.PersonsToPersonBasicDataDtos(personService.getAllPersons());
    }

    @GetMapping("/person/basic")
    public @ResponseBody AuthenticationResponse getLoggedPersonBasicData(@RequestParam Long id_person){


        Optional<Person> optionalPerson = personService.getPersonById(id_person);

        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            UserData userData = person.getUserData();

            String role;
            if (userData.isGod()){
                role = "GOD";
            } else if (userData.isAdmin()) {
                role = "ADMIN";
            } else {
                role = "USER";
            }

            return AuthenticationResponse.builder()
                    .loggedUserId(person.getId_person())
                    .role(role)
                    .loggedUserClubsIds(person.getClubsIds())
                    .loggedUserOwnedClubsIds(person.getOwnedClubsIds())
                    .loggedUserJoinedEventsIds(person.getJoinedEventsIds())
                    .loggedUserAppliedClubsIds(person.getAppliedClubsIds())
                    .loggedUserAppliedEventsIds(person.getRequestEventsIds())
                    .build();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono encji");
        }


    }



}