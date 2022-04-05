package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;

import java.util.List;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "club_name", expression = "java(person.getClub_name())")
    PersonDTO PersonToPersonDto(Person person);

    @Mapping(target = "club", ignore = true)
    Person PersonDtoToPerson(PersonDTO personDTO);

    List<PersonDTO> PersonsToPersonDtos(List<Person> persons);

}
