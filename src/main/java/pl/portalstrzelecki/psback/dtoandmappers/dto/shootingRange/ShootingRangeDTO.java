package pl.portalstrzelecki.psback.dtoandmappers.dto.shootingRange;

import lombok.Data;

@Data
public class ShootingRangeDTO {

    private Long id_shootingrange;
    private String name;
    private String description;
    private String adress;

    public boolean notNull() {
        return !(
                id_shootingrange == null ||
                name == null ||
                description == null ||
                adress == null
        );
    }

}
