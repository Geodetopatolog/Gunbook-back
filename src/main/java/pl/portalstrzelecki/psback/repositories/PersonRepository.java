package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.portalstrzelecki.psback.domain.person.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
