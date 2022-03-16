package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.person.Person;

public interface PersonService {

    void savePerson(Person person);
    void deletePerson(Person person);
    void updatePerson(Long id, Person person);
    Person getPersonById(long id);
}
