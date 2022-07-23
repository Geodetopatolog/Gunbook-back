package pl.portalstrzelecki.psback.domain.Authentication;

import lombok.*;
import pl.portalstrzelecki.psback.domain.person.Person;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_user;

    private String username;
    private String password;
    private boolean isActive = false;
    private boolean isUser = false;
    private boolean isAdmin = false;
    private boolean isGod = false;

    @OneToOne
    private Person person;


}
