package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;

import java.util.List;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "clubs_name", expression = "java(person.getClubsName())")
    PersonDTO PersonToPersonDto(Person person);

    @Mapping(target = "clubs", ignore = true)
    Person PersonDtoToPerson(PersonDTO personDTO);

    List<PersonDTO> PersonsToPersonDtos(List<Person> persons);

}
