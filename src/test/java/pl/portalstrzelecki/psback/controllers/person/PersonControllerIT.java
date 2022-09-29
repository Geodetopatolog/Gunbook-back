package pl.portalstrzelecki.psback.controllers.person;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationRequest;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationResponse;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonBasicDataDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonDTO;
import pl.portalstrzelecki.psback.dtoandmappers.dto.person.PersonRegistrationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@TestPropertySource("/persistence-generic-entity.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIT {
//todo ogarnąć te testy po zmianie działania tokena
//stary token eyJhbGciOiJIUzI1NiJ9.eyJpZCI6Miwic3ViIjoidXNlciIsImlhdCI6MTY2MzkzMDk5MywiZXhwIjoxNjYzOTM4MTkzfQ.3nSc06wRyMeP2aE1H2TGuYUeCikm8-1L9-oGWtxU0qw
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    private final int PERSON_IN_DB_COUNT = 4;


    private URI createServerAddress(String suffix) throws URISyntaxException {
        return new URI("http://localhost:" + serverPort + suffix);
    }

    private String getJWTToken (String authority) throws URISyntaxException {
        AuthenticationRequest authenticationRequest;
        if (authority == "USER") {
            authenticationRequest = new AuthenticationRequest("user", "haslo");
        } else if (authority == "ADMIN") {
            authenticationRequest = new AuthenticationRequest("admin", "haslo");
        } else {
            authenticationRequest = new AuthenticationRequest("god", "haslo");
        }

        RequestEntity<AuthenticationRequest> request = RequestEntity
                .post(createServerAddress("/authenticate"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationRequest);

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(request, AuthenticationResponse.class);

        return "Bearer " + response.getBody().getJwt();
    }


    @Test
    @Sql(statements = "DELETE FROM person WHERE opis = 'shouldReturn2xxWhenAddPersonSuccessfully';",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn2xxWhenAddPersonSuccessfully() throws Exception {

        //given
        PersonRegistrationDTO personRegistrationDTO = PersonRegistrationDTO.builder()
//                .id_person(1L)
                .description("shouldReturn2xxWhenAddPersonSuccessfully")
                .email("jakis@email.com")
                .name("Imie")
                .surname("Nazwisko")
                .nick("Koles")
                .password("haslo")
                .username("login")
                .build();


        String token = getJWTToken("USER");

        //when

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PersonRegistrationDTO> entity = new HttpEntity<>(personRegistrationDTO, headers);

        ResponseEntity<PersonDTO> response = restTemplate.exchange(createServerAddress("/person"), HttpMethod.POST, entity, PersonDTO.class);

        //then

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    void shouldReturn2xxAndPersonWhenGetPersonSuccessfully() throws Exception {

        //given
        PersonDTO personDTO = PersonDTO.builder()
                .id_person(1L)
                .clubs_name(new ArrayList<>())
                .description("a")
                .name("Rafał")
                .surname("Szatkowski")
                .nick("Zenek")
                .email("jakis@email.com")
                .build();


        String token = getJWTToken("USER");

        //when

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PersonDTO> entity = new HttpEntity<>(personDTO, headers);

        ResponseEntity<PersonDTO> response = restTemplate.exchange(createServerAddress("/person?id_person=1"), HttpMethod.GET, entity, PersonDTO.class);


        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(personDTO, response.getBody());

    }


//    @Test //póki co nie przejdzie, bo nie ma zaimplementowanego sprawdzania :P takie małe TDD
    @Sql(statements = {"DELETE FROM person WHERE opis = 'shouldReturn4xxWhenAddPersonWithIncompleteLoginData1';",
            "DELETE FROM person WHERE opis = 'shouldReturn4xxWhenAddPersonWithIncompleteLoginData2';"}
            , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn4xxWhenAddPersonWithIncompleteLoginData() throws Exception {
        //given
        PersonRegistrationDTO personRegistrationDTO1 = PersonRegistrationDTO.builder()
                .id_person(1002L)
                .description("shouldReturn4xxWhenAddPersonWithIncompleteLoginData1")
                .email("jakis@email.com")
                .name("Imie")
                .surname("Nazwisko")
                .nick("Koles")
                //.password("haslo")
                .username("login")
                .build();

        PersonRegistrationDTO personRegistrationDTO2 = PersonRegistrationDTO.builder()
                .id_person(1002L)
                .description("shouldReturn4xxWhenAddPersonWithIncompleteLoginData2")
                .email("jakis@email.com")
                .name("Imie")
                .surname("Nazwisko")
                .nick("Koles")
                .password("haslo")
//                .username("login")
                .build();

        //when
        RequestEntity<PersonRegistrationDTO> request1 = RequestEntity
                .post(createServerAddress("/person"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(personRegistrationDTO1);

        ResponseEntity<HttpStatus> response1 = restTemplate.exchange(request1, HttpStatus.class);

        RequestEntity<PersonRegistrationDTO> request2 = RequestEntity
                .post(createServerAddress("/person"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(personRegistrationDTO2);

        ResponseEntity<HttpStatus> response2 = restTemplate.exchange(request2, HttpStatus.class);

        //then
        Assertions.assertEquals(400, response1.getStatusCode().value());
        Assertions.assertEquals(400, response2.getStatusCode().value());
    }




    @Test
    @Sql(statements = "DELETE FROM person WHERE ID = 1001;",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturn404WhenGetPersonNotFound() throws Exception {

        //given
        String token = getJWTToken("USER");

        //when

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<PersonDTO> response = restTemplate.exchange(createServerAddress("/person?id_person=1000"), HttpMethod.GET, entity, PersonDTO.class);

        //then
        Assertions.assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void shouldReturn404WhenURIParamMissing() throws Exception {

        //given
        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<PersonDTO> response = restTemplate.exchange(createServerAddress("/person"), HttpMethod.GET, entity, PersonDTO.class);

        //then
        Assertions.assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void shouldReturn2xxWhenPatchPersonSuccessfully() throws Exception {

        //given
        PersonDTO personDTO = PersonDTO.builder()
                .id_person(1L)
                .description("a")
                .email("jakisinny@email.com")
                .name("Rafał")
                .surname("Szatkowski")
                .nick("Zenek")
                .build();

        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PersonDTO> entity = new HttpEntity<>(personDTO, headers);

        ResponseEntity<PersonDTO> response = restTemplate.exchange(createServerAddress("/person"), HttpMethod.PATCH, entity, PersonDTO.class);

        //then
        System.out.println(response.getStatusCodeValue());
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldReturn404WhenPatchPersonNotFound() throws Exception {

        //given
        PersonDTO personDTO = PersonDTO.builder()
                .id_person(1000L)
                .description("a")
                .email("jakisinny@email.com")
                .name("Rafał")
                .surname("Szatkowski")
                .nick("Zenek")
                .build();

        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PersonDTO> entity = new HttpEntity<>(personDTO, headers);

        ResponseEntity<PersonDTO> response = restTemplate.exchange(createServerAddress("/person"), HttpMethod.PATCH, entity, PersonDTO.class);

        //then
        System.out.println(response.getStatusCodeValue());
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    void shouldReturn400WhenPatchPersonIncompleteData() throws Exception {

        //given
        PersonDTO personDTO = PersonDTO.builder()
                .id_person(1L)
                .description(null)
                .email("jakisinny@email.com")
                .name("Rafał")
                .surname("Szatkowski")
                .nick("Zenek")
                .build();

        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PersonDTO> entity = new HttpEntity<>(personDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(createServerAddress("/person"), HttpMethod.PATCH, entity, String.class);

        //then
        System.out.println(response.getStatusCodeValue());
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @Sql (statements = "insert into person (ID, Opis, Imię, Nazwisko, Nick, Email) values " +
    "(1, 'a', 'Rafał', 'Szatkowski', 'Zenek', 'jakis@email.com');", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn2xxWhenDeletePersonSuccessfully() throws Exception {

        //given
        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(createServerAddress("/person?id_person=1"), HttpMethod.DELETE, entity, String.class);

        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldReturn404WhenDeletePersonNotFound() throws Exception {
        //given
        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(createServerAddress("/person?id_person=1000"), HttpMethod.DELETE, entity, String.class);

        //then
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    void shouldReturnAllExistingPeople() throws Exception {

        //given
        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<List<PersonDTO>> response = restTemplate.exchange(createServerAddress("/person/all"),
                HttpMethod.GET, entity, new ParameterizedTypeReference<>(){});

        //then:
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(PERSON_IN_DB_COUNT, response.getBody().size());

    }


    @Test
    void shouldReturnAllExistingPeopleBasicData() throws Exception {
        //given
        String token = getJWTToken("USER");

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<List<PersonBasicDataDTO>> response = restTemplate.exchange(createServerAddress("/person/all/basic"),
                HttpMethod.GET, entity, new ParameterizedTypeReference<>(){});

        //then:
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(PERSON_IN_DB_COUNT, response.getBody().size());

    }


}
