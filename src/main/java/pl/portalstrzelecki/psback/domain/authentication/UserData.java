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
    @Column(name = "ID")
    private Long id_user;

    @Column(name = "Login")
    private String username;
    @Transient
    private String password;
    @Column(name = "Hasło")
    private String encryptedPassword;
    @Column(name = "Aktywny")
    private boolean isActive = true;
    @Column(name = "USER")
    private boolean isUser = true;
    @Column(name = "ADMIN")
    private boolean isAdmin = false;
    @Column(name = "GOD")
    private boolean isGod = false;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name = "ID_użytkownika")
    private Person person;

    public UserData() {
    }
}
