package pl.portalstrzelecki.psback.domain.club;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.person.Person;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String logoURL = "";
    private String name = "";
    private String description = "";
//    private List<Person> owners;
//    private List<Person> members;
    private boolean sport=false;
    private boolean fun=false;
    private boolean cours=false;


    public Club() {
    }


    public Club updateClub(Club club) {
        this.logoURL = club.getLogoURL();
        this.name = club.getName();
        this.description = club.getDescription();
        this.sport = club.isSport();
        this.fun = club.isFun();
        this.cours = club.isCours();
        return this;
    }
}
