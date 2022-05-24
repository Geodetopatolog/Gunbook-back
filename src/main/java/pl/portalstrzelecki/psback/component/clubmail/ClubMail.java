package pl.portalstrzelecki.psback.component.clubmail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.component.mailer.Mailer;
import pl.portalstrzelecki.psback.domain.club.Club;
import pl.portalstrzelecki.psback.domain.person.Person;

@Service
@RequiredArgsConstructor
public class ClubMail {

    private final Mailer mailer;

    public void memberRequestMail(Club club, Person person) {
        String email = club.getEmail();
        String subject = "Proźba o przyjęcie do klubu";
        String text = "Niejaki " + person.getName() + " " + person.getSurname() + " przedstawiający się jako " +
                person.getNick() + " prosi o przyjęcie do klubu.";

        mailer.sendMessage(email, subject, text);
    }

}
