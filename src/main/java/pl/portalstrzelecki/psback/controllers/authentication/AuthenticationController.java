package pl.portalstrzelecki.psback.controllers.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.portalstrzelecki.psback.component.security.JwtUtil;
import pl.portalstrzelecki.psback.component.security.MyUserDetailsService;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationRequest;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationResponse;
import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.services.PersonService;
import pl.portalstrzelecki.psback.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@CrossOrigin(origins = "${cors.origin}", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;
    private final PersonService personService;
    private final JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {


       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
           );
       } catch (BadCredentialsException e) {
           throw new Exception("Błędny login lub hasło", e);
       }

       final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

//        String role;
//
//        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("GOD"))){
//            role = "GOD";
//       } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
//            role = "ADMIN";
//       } else {
//            role = "USER";
//       }



        Optional<UserData> optionalUserByUsername = userService.getUserByUsername(userDetails.getUsername());

        if (optionalUserByUsername.isPresent())
        {
            Person personByUsername = optionalUserByUsername.get().getPerson();

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", personByUsername.getId_person());

            final String jwt = jwtUtil.generateToken(userDetails, claims);

//            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
//                    .jwt(jwt)
//                    .loggedUserId(personByUsername.getId_person())
//                    .role(role)
//                    .loggedUserClubsIds(personByUsername.getClubsIds())
//                    .loggedUserOwnedClubsIds(personByUsername.getOwnedClubsIds())
//                    .loggedUserJoinedEventsIds(personByUsername.getJoinedEventsIds())
//                    .loggedUserAppliedClubsIds(personByUsername.getAppliedClubsIds())
//                    .loggedUserAppliedEventsIds(personByUsername.getRequestEventsIds())
//                    .build();
//
//            System.out.println(authenticationResponse.toString());


//ciastka nie https nie moga być secure a bez secure nie mogą być sameSite None :P
            ResponseCookie cookieJWT = ResponseCookie.from("Authorization", jwt)
                    .httpOnly(true)
                    .secure(false)
//                    .domain("gunbook.eu")
                    .path("/")
                     .sameSite("None")
                    .maxAge(-1)
                    .build();

            ResponseCookie cookieIdPerson = ResponseCookie.from("IdPerson", personByUsername.getId_person().toString())
                    .httpOnly(false)
                    .secure(false)
//                    .domain("gunbook.eu")
                    .path("/")
                    .sameSite("None")
                    .maxAge(60)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookieJWT.toString())
                    .header(HttpHeaders.SET_COOKIE, cookieIdPerson.toString())
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @CrossOrigin
//            (origins = "http://localhost", allowCredentials = "true")
//    @GetMapping("/basicdata")
//    public @ResponseBody AuthenticationResponse getLoggedPersonBasicData(@RequestParam Long id_person){
//
//
//            Optional<Person> optionalPerson = personService.getPersonById(id_person);
//
//            if (optionalPerson.isPresent()) {
//                Person person = optionalPerson.get();
//
//                UserData userData = person.getUserData();
//
//                String role;
//                if (userData.isGod()){
//                    role = "GOD";
//                } else if (userData.isAdmin()) {
//                    role = "ADMIN";
//                } else {
//                    role = "USER";
//                }
//
//                return AuthenticationResponse.builder()
//                        .loggedUserId(person.getId_person())
//                        .role(role)
//                        .loggedUserClubsIds(person.getClubsIds())
//                        .loggedUserOwnedClubsIds(person.getOwnedClubsIds())
//                        .loggedUserJoinedEventsIds(person.getJoinedEventsIds())
//                        .loggedUserAppliedClubsIds(person.getAppliedClubsIds())
//                        .loggedUserAppliedEventsIds(person.getRequestEventsIds())
//                        .build();
//            } else {
//                throw new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Nie znaleziono encji");
//            }
//
//
//    }

    }
