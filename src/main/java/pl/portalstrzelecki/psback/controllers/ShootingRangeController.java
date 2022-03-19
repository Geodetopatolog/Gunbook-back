package pl.portalstrzelecki.psback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;
import pl.portalstrzelecki.psback.services.ShootingRangeService;

import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class ShootingRangeController {

    @Autowired
    ShootingRangeService shootingRangeService;

    @PostMapping("/range")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ShootingRange saveShootingRange(@RequestBody ShootingRange shootingRange) {
        shootingRangeService.saveShootingRange(shootingRange);
        return shootingRange;
    }

    @GetMapping("/range")
    public @ResponseBody
    ShootingRange getShootingRangeById(@RequestBody ShootingRange shootingRange) {
        Optional<ShootingRange> optionalShootingRange = shootingRangeService.getShootingRangeById(shootingRange.getId());

        if (optionalShootingRange.isPresent()) {
            return optionalShootingRange.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PatchMapping("/range")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateShootingRange(@RequestBody ShootingRange shootingRange) {
        shootingRangeService.updateShootingRange(shootingRange.getId(), shootingRange);
    }

    @DeleteMapping("/range")
    public ResponseEntity<?> deleteShootingRange(@RequestBody ShootingRange shootingRange) {
        boolean anyShootingRangeRemoved = shootingRangeService.deleteShootingRange(shootingRange.getId());

        if (anyShootingRangeRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ranges")
    public @ResponseBody
    List<ShootingRange> getAllShootingRanges() {
        return shootingRangeService.getAllShootingRanges();
    }


}
