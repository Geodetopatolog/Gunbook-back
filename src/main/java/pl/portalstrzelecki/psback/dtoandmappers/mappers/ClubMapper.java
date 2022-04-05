package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.dtoandmappers.dto.club.ClubDTO;

import java.util.List;

@Mapper
public interface ClubMapper {

    ClubMapper INSTANCE = Mappers.getMapper(ClubMapper.class);

    ClubDTO ClubToClubDto (Club club);

    Club ClubDtoToClub (ClubDTO clubDTO);

    List<ClubDTO> ClubToClubDtos(List<Club> clubs);

}
