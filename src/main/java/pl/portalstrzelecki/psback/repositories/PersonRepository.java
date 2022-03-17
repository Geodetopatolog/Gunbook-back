package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    @Query("select p from Person p where p.name = 'Rafał'")
    public List<Person> getPersonWithNameEqualsRafal();

    @Query("select p from Person p where p.name = :queryName")
    public List<Person> getPersonWithNameEquals(@Param("queryName") String name);

}
