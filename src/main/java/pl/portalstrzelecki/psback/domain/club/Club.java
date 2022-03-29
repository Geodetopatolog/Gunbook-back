package pl.portalstrzelecki.psback.domain.club;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.domain.shootingrange.ShootingRange;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id_club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_club;

    private String logoURL = "";
    private String name = "";
    private String description = "";
//    private List<Person> owners;

    @OneToMany(mappedBy = "club")
    private List<Person> members;

    @OneToMany (mappedBy = "residentClub")
    private List<ShootingRange> ranges;

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

    public void addMember(Person member) {
        if (members == null) {
            members = new ArrayList<>();
        }
        members.add(member);
        member.setClub(this);
    }

    public boolean deleteMember(Person person) {
        if (members==null) {
            return false;
        } else {
            members.stream().forEach(member -> {
                if (member.getId_person()==person.getId_person()) {
                    member.resetClub();
                }
            });
            return true;
        }
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
