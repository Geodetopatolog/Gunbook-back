package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;

import java.util.List;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "organizers", expression = "java(event.getOrganizersName())")
    @Mapping(target = "participants", expression = "java(event.getParticipantCount())")
    @Mapping(target = "range", expression = "java(event.getPlaceName())")
    EventDTO EventToEventDto (Event event);

    @Mapping(target = "organizers", ignore = true)
    @Mapping(target = "place", ignore = true)
    @Mapping(target = "participants", ignore = true)
    Event EventDtoToEvent (EventDTO eventDTO);

    List<EventDTO> EventsToEventDtos(List<Event> events);

}
