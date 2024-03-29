package pl.portalstrzelecki.psback.domain.shootingrange;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.event.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id_shootingrange")
public class ShootingRange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id_shootingrange;

    @Column(name = "Nazwa")
    private String name;
    @Column(name = "Opis")
    private String description;
    @Column(name = "Adres")
    private String adress;

    @ManyToMany
    @JoinTable(
            name = "clubs_ranges",
            joinColumns = @JoinColumn(name = "ID_strzelnicy"),
            inverseJoinColumns = @JoinColumn(name = "ID_klubu")
    )
    private List<Club> clubs = new ArrayList<>();


    @OneToMany(mappedBy = "place", cascade = CascadeType.MERGE)
    private List<Event> events;


    public ShootingRange() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public ShootingRange updateShootingRange(ShootingRange shootingRange) {
        this.name = shootingRange.getName();
        this.description = shootingRange.getDescription();
        this.adress = shootingRange.getAdress();
        return this;
    }

    public void addClub(Club club) {
        clubs.add(club);
    }

    public void deleteClub(Club club) {
        clubs.remove(club);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }


}
