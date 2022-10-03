package pl.portalstrzelecki.psback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PSbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PSbackApplication.class, args);
    }

}
//TODO serwis mailowy, powiadomienia o zmianach w eventach, nowych eventach, wiadomości od klubów itp