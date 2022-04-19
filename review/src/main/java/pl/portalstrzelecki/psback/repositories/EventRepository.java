package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.portalstrzelecki.psback.domain.event.Event;

public interface EventRepository extends CrudRepository<Event, Long> {


}
