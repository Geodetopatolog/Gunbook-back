package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubRegistrationDTO;

import java.util.List;

@Mapper
public interface ClubMapper {

    ClubMapper INSTANCE = Mappers.getMapper(ClubMapper.class);


    ClubDTO ClubToClubDto (Club club);
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "owners", ignore = true)
    @Mapping(target = "ranges", ignore = true)
    @Mapping(target = "membershipRequests", ignore = true)
    Club ClubDtoToClub (ClubDTO clubDTO);

    List<ClubDTO> ClubToClubDtos(List<Club> clubs);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "members", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "owners", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "ranges", ignore = true)
    @Mapping(target = "membershipRequests", ignore = true)
    Club ClubRegistrationDTOtoClub  (ClubRegistrationDTO clubRegistrationDTO);

}
