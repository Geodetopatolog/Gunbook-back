package pl.portalstrzelecki.psback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class PSbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PSbackApplication.class, args);
    }




}

//todo zakładanie klubu z panelu użytkownika i tworzenie relacji club - club owner + member
//todo tworzenie eventu od razu z panelu klubu i zapisywanie relacji event - club tak jak event - strzelnica
//todo zakładanie strzelnicy z panelu klubu i tworzenie relacji club - ranges
//todo wywalić niepotrzebne endpointy po tym wszystkim




//TODO serwis mailowy, powiadomienia o zmianach w eventach, nowych eventach, wiadomości od klubów itp
