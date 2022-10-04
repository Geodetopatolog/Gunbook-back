package pl.portalstrzelecki.psback.controllers.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.portalstrzelecki.psback.component.security.JwtUtil;
import pl.portalstrzelecki.psback.component.security.MyUserDetailsService;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationRequest;
import pl.portalstrzelecki.psback.domain.authentication.AuthenticationResponse;
import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.services.UserService;

import java.util.*;

@CrossOrigin(origins = "${cors.origin}", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;
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

        String role;

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("GOD"))){
            role = "GOD";
       } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            role = "ADMIN";
       } else {
            role = "USER";
       }



        Optional<UserData> optionalUserByUsername = userService.getUserByUsername(userDetails.getUsername());

        if (optionalUserByUsername.isPresent())
        {
            Person personByUsername = optionalUserByUsername.get().getPerson();

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", personByUsername.getId_person());

            final String jwt = jwtUtil.generateToken(userDetails, claims);

            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .jwt(jwt)
                    .loggedUserId(personByUsername.getId_person())
                    .role(role)
                    .loggedUserClubsIds(personByUsername.getClubsIds())
                    .loggedUserOwnedClubsIds(personByUsername.getOwnedClubsIds())
                    .loggedUserJoinedEventsIds(personByUsername.getJoinedEventsIds())
                    .loggedUserAppliedClubsIds(personByUsername.getAppliedClubsIds())
                    .loggedUserAppliedEventsIds(personByUsername.getRequestEventsIds())
                    .build();


            return ResponseEntity.ok(authenticationResponse);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
