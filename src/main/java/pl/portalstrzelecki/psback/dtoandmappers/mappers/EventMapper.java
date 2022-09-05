package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.event.Event;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.event.EventRegistrationDTO;

import java.util.List;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

//    @Mapping(target = "organizers", expression = "java(event.getOrganizersName())")
//    @Mapping(target = "participants", expression = "java(event.getParticipantCount())")
    @Mapping(target = "rangeName", expression = "java(event.getRangeName())")
    EventDTO EventToEventDto (Event event);

    @Mapping(target = "organizers", ignore = true)
    @Mapping(target = "place", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "participantsRequests", ignore = true)
    @Mapping(target = "rangeName", ignore = true)
    Event EventDtoToEvent (EventDTO eventDTO);

    List<EventDTO> EventsToEventDtos(List<Event> events);

    @Mapping(target = "organizers", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "place", ignore = true)
    @Mapping(target = "participantsRequests", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "participants", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "participantsCount", ignore = true)
    Event EventRegostrationDtoToEvent (EventRegistrationDTO eventRegistrationDTO);

}
