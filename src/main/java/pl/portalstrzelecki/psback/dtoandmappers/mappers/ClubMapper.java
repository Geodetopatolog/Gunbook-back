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

    @Mapping(target = "members_count", expression = "java(club.getMembers().size())")
    ClubDTO ClubToClubDto (Club club);

    @Mapping(target = "members", ignore = true)
    Club ClubDtoToClub (ClubDTO clubDTO);

    List<ClubDTO> ClubToClubDtos(List<Club> clubs);

}
