package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;

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

}
