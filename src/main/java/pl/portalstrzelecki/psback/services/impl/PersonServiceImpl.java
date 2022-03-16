package pl.portalstrzelecki.psback.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.repositories.PersonRepository;
import pl.portalstrzelecki.psback.services.PersonService;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    @Override
    public void updatePerson(Long id, Person person) {
        Optional<Person> optionalPerson= personRepository.findById(id);
        if(optionalPerson.isPresent()) {
            personRepository.save(optionalPerson.get().updatePerson(person));
        }
        else {
            throw new RuntimeException("Nie można znaleźć człowieka");
        }


    }

    @Override
    public Person getPersonById(long id) {
        Optional<Person> optionalPerson= personRepository.findById(id);
        if(optionalPerson.isPresent()) {
            return optionalPerson.get();
        }
        else {
            throw new RuntimeException("Nie można znaleźć człowieka");
        }
    }

}
