package pl.portalstrzelecki.psback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PSbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PSbackApplication.class, args);
    }




}
//todo TESTY TESTY TESTY! :P

//todo zdecydować się na nazewnictwo typu request, application itp :)

//todo w tokenie mamy już id zalogowanego i porównuje je z id z zapytania http, teraz tylko zostało dodać sprawdzanie poziomu authority i dodanie tego wszędzie :P

//todo zakładanie strzelnicy z panelu klubu i tworzenie relacji club - ranges
//todo wywalić niepotrzebne endpointy po tym wszystkim

//TODO serwis mailowy, powiadomienia o zmianach w eventach, nowych eventach, wiadomości od klubów itp
