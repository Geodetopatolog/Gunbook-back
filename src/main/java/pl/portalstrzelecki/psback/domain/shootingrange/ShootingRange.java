package pl.portalstrzelecki.psback.domain.shootingrange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Builder
@Entity
public class ShootingRange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String adress;


    public ShootingRange() {

    }

    public ShootingRange updateShootingRange(ShootingRange shootingRange) {
        this.name = shootingRange.getName();
        this.description = shootingRange.getDescription();
        this.adress = shootingRange.getAdress();
        return this;
    }
}
