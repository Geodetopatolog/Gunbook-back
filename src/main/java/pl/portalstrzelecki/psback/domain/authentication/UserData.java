package pl.portalstrzelecki.psback.domain.authentication;

import lombok.*;
import pl.portalstrzelecki.psback.domain.person.Person;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@Builder
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_user;

    private String username;
    @Transient
    private String password;
    private String encryptedPassword;
    private boolean isActive = true;
    private boolean isUser = true;
    private boolean isAdmin = false;
    private boolean isGod = false;

    @OneToOne (cascade=CascadeType.ALL)
    private Person person;

    public UserData() {


    }
}
