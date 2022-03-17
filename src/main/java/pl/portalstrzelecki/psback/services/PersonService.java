package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;

public interface PersonService {

    void savePerson(Person person);
    void deletePerson(Long id);
    void updatePerson(Long id, Person person);
    Person getPersonById(long id);

    List<Person> getAllPersons();

    public List<Person> getPersonWithNameEqualsRafal();
    public List<Person> getPersonWithNameEquals(String name);



}
