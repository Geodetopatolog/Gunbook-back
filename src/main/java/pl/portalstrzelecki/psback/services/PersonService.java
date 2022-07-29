package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    void savePerson(Person person, UserData userData);
    boolean deletePerson(Long id);
    boolean updatePerson(Person person);
    Optional<Person> getPersonById(long id);

    List<Person> getAllPersons();

    List<Person> getPersonWithNameEquals(String name);



}
