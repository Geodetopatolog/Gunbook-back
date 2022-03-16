package pl.portalstrzelecki.psback.domain.event.competition;

import lombok.Data;
import pl.portalstrzelecki.psback.domain.event.Event;

@Data
public class Competition extends Event {

    private boolean cupCounted = false;


}
