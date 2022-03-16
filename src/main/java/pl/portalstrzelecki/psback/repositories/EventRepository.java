package pl.portalstrzelecki.psback.repositories;

import org.springframework.stereotype.Repository;
import pl.portalstrzelecki.psback.domain.event.Event;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {

    private List<Event> events = new ArrayList<>();

    public void createEvent (Event event) {
        events.add(event);
    }



}
