package pl.portalstrzelecki.psback.domain.shootingrange;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id_shootingrange")
public class ShootingRange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_shootingrange;

    private String name;
    private String description;
    private String adress;
    @ManyToOne
    @JoinColumn (name = "id_club")
    private Club residentClub;

//    private List<Club> clubs;


    public ShootingRange() {

    }

    public ShootingRange updateShootingRange(ShootingRange shootingRange) {
        this.name = shootingRange.getName();
        this.description = shootingRange.getDescription();
        this.adress = shootingRange.getAdress();
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
