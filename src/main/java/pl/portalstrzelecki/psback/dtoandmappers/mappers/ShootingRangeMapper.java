package pl.portalstrzelecki.psback.dtoandmappers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange.ShootingRangeDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange.ShootingRangeNameDTO;

import java.util.List;

@Mapper
public interface ShootingRangeMapper {

    ShootingRangeMapper INSTANCE = Mappers.getMapper(ShootingRangeMapper.class);


    ShootingRangeDTO ShootingRangeToShootingRangeDTO (ShootingRange shootingRange);
    ShootingRange ShootingRangeDtoToShootingRange (ShootingRangeDTO shootingRangeDTO);
    List<ShootingRangeDTO> ShootingRangesToShootingRangeDtos (List<ShootingRange> shootingRanges);


    ShootingRangeNameDTO ShootingRangeToShootingRangeNameDTO (ShootingRange shootingRange);
    List<ShootingRangeNameDTO> ShootingRangesToShootingRangeNameDTOs (List<ShootingRange> shootingRanges);

}
