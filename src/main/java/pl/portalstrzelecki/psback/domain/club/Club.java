package pl.portalstrzelecki.psback.domain.club;

import lombok.Data;
import pl.portalstrzelecki.psback.domain.person.Person;

import java.util.List;

@Data
public class Club {

    private String name;
    private String description;
    private List<Person> owners;
    private List<Person> members;
    private boolean sport=false;
    private boolean fun=false;
    private boolean cours=false;


}
