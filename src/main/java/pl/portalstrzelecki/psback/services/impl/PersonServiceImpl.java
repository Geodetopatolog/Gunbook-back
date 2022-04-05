package pl.portalstrzelecki.psback.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.repositories.PersonRepository;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void savePerson(Person person) {
        person.setId_person(null);
        personRepository.save(person);
    }

    @Override
    public boolean deletePerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if(optionalPerson.isPresent()) {
            personRepository.delete(optionalPerson.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updatePerson(Person person) {
        Optional<Person> optionalPerson = personRepository.findById(person.getId_person());
        if(optionalPerson.isPresent()) {
            personRepository.save(optionalPerson.get().updatePerson(person));
//            personRepository.save(person);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Optional<Person> getPersonById(long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
            return optionalPerson;
    }

    @Override
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public List<Person> getPersonWithNameEqualsRafal() {
        return personRepository.getPersonWithNameEqualsRafal();
    }

    @Override
    public List<Person> getPersonWithNameEquals(String name) {
        return personRepository.getPersonWithNameEquals(name);
    }

}
