package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;

import java.util.List;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDTO EventToEventDto (Event event);

    Event EventDtoToEvent (EventDTO eventDTO);

    List<EventDTO> EventsToEventDtos(List<Event> events);

}
