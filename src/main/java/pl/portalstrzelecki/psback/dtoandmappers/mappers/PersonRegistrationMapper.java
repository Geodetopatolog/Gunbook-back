package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonRegistrationDTO;

@Mapper
public interface PersonRegistrationMapper {

    PersonRegistrationMapper INSTANCE = Mappers.getMapper(PersonRegistrationMapper.class);

    @Mapping(target = "clubs", ignore = true)
    @Mapping(target = "eventsJoined", ignore = true)
    @Mapping(target = "ownedClubs", ignore = true)
    @Mapping(target = "userData", ignore = true)
    Person PersonRegistrationDTOToPerson (PersonRegistrationDTO personRegistrationDTO);

    @Mapping(target = "isActive", expression = "java(true)")
    @Mapping(target = "isUser", expression = "java(true)")
    @Mapping(target = "isAdmin", expression = "java(false)")
    @Mapping(target = "isGod", expression = "java(false)")
    @Mapping(target = "encryptedPassword", ignore = true)
    @Mapping(target = "id_user", ignore = true)
    @Mapping(target = "person", ignore = true)
    UserData PersonRegistrationDTOToUserData (PersonRegistrationDTO personRegistrationDTO);
}
