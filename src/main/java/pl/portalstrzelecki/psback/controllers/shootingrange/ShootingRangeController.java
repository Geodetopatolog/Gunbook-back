package pl.portalstrzelecki.psback.controllers.shootingrange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange.ShootingRangeDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange.ShootingRangeNameDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange.ShootingRangeRegistrationDTO;
import pl.portalstrzelecki.psback.dtoandmappers.mappers.ShootingRangeMapper;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ShootingRangeController {

    private final ShootingRangeService shootingRangeService;


    @PostMapping("/range")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveShootingRange(@RequestBody ShootingRangeRegistrationDTO shootingRangeRegistrationDTO) {
        ShootingRange shootingRange = ShootingRangeMapper.INSTANCE.ShootingRangeRegistrationDtoToShootingRange(shootingRangeRegistrationDTO);
        shootingRangeService.saveShootingRange(shootingRange, shootingRangeRegistrationDTO.getId_club());
    }

    @GetMapping("/range")
    public @ResponseBody ShootingRangeDTO getShootingRangeById(@RequestParam Long id_range) {

        Optional<ShootingRange> optionalShootingRange = shootingRangeService.getShootingRangeById(id_range);

        if (optionalShootingRange.isPresent()) {
            return ShootingRangeMapper.INSTANCE.ShootingRangeToShootingRangeDTO(optionalShootingRange.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }


    @GetMapping("/rangebyname")
    public @ResponseBody ShootingRangeDTO getShootingRangeById(@RequestParam String name) {

        Optional<ShootingRange> optionalShootingRange = shootingRangeService.getShootingRangeByName(name);

        if (optionalShootingRange.isPresent()) {
            return ShootingRangeMapper.INSTANCE.ShootingRangeToShootingRangeDTO(optionalShootingRange.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }



    @PatchMapping("/range")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateShootingRange(@RequestBody ShootingRangeDTO shootingRangeDTO) {

        if (shootingRangeDTO.notNull()) {
            ShootingRange shootingRange = ShootingRangeMapper.INSTANCE.ShootingRangeDtoToShootingRange(shootingRangeDTO);
            if (shootingRangeService.updateShootingRange(shootingRange)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Nieprawidłowe dane");
        }
    }

    @DeleteMapping("/range")
    public ResponseEntity<?> deleteShootingRange(@RequestParam Long id_range) {

        boolean anyShootingRangeRemoved = shootingRangeService.deleteShootingRange(id_range);

        if (anyShootingRangeRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/range/all")
    public @ResponseBody List<ShootingRangeDTO> getAllShootingRanges() {

        List<ShootingRange> allShootingRanges = shootingRangeService.getAllShootingRanges();

        return ShootingRangeMapper.INSTANCE.ShootingRangesToShootingRangeDtos(allShootingRanges);

    }

    @GetMapping("/range/ranges_names")
    public @ResponseBody List<ShootingRangeNameDTO> getAllShootingRangesNames() {

        List<ShootingRange> allShootingRanges = shootingRangeService.getAllShootingRanges();

        return ShootingRangeMapper.INSTANCE.ShootingRangesToShootingRangeNameDTOs(allShootingRanges);

    }


}
