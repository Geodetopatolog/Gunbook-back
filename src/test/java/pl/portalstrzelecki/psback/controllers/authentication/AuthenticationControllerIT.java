package pl.portalstrzelecki.psback.controllers.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationRequest;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationResponse;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource("/persistence-generic-entity.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerIT {


    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    private URI createServerAddress(String suffix) throws URISyntaxException {
        return new URI("http://localhost:" + serverPort + suffix);
    }


    @Test
    void shouldLoginAndReturn2xxWhenAuthenticateSuccessfully() throws URISyntaxException {

        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user", "haslo");

        //when
        RequestEntity<AuthenticationRequest> request = RequestEntity
                .post(createServerAddress("/authenticate"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationRequest);

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(request, AuthenticationResponse.class);

        //then
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }


    @Test
    void shouldReturn4xxWhenAuthenticateUnsuccessfull() throws URISyntaxException {

        //given
        AuthenticationRequest authenticationRequest1 = new AuthenticationRequest("user", "blednehaslo");
        AuthenticationRequest authenticationRequest2 = new AuthenticationRequest("notuser", "haslo");

        //when
        RequestEntity<AuthenticationRequest> request1 = RequestEntity
                .post(createServerAddress("/authenticate"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationRequest1);

        RequestEntity<AuthenticationRequest> request2 = RequestEntity
                .post(createServerAddress("/authenticate"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationRequest2);

        ResponseEntity<AuthenticationResponse> response1 = restTemplate.exchange(request1, AuthenticationResponse.class);
        ResponseEntity<AuthenticationResponse> response2 = restTemplate.exchange(request2, AuthenticationResponse.class);

        //then

        assertTrue(response1.getStatusCode().is4xxClientError());
        assertTrue(response2.getStatusCode().is4xxClientError());


    }




}