package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.Authentication.UserData;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonRegistrationDTO;

@Mapper
public interface PersonRegistrationMapper {

    PersonRegistrationMapper INSTANCE = Mappers.getMapper(PersonRegistrationMapper.class);

    Person PersonRegistrationDTOToPerson (PersonRegistrationDTO personRegistrationDTO);

    @Mapping(target = "isActive", expression = "java(true)")
    @Mapping(target = "isUser", expression = "java(true)")
    @Mapping(target = "isAdmin", expression = "java(false)")
    @Mapping(target = "isGod", expression = "java(false)")
    UserData PersonRegistrationDTOToUserData (PersonRegistrationDTO personRegistrationDTO);
}
