package pl.portalstrzelecki.psback.controllers.login;

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
import pl.portalstrzelecki.psback.domain.Authentication.AuthenticationRequest;
import pl.portalstrzelecki.psback.domain.Authentication.AuthenticationResponse;
import pl.portalstrzelecki.psback.domain.Authentication.UserData;
import pl.portalstrzelecki.psback.domain.person.Person;
import pl.portalstrzelecki.psback.services.UserService;

import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        System.out.println(authenticationRequest);

       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
           );
       } catch (BadCredentialsException e) {
           throw new Exception("Błędny login lub hasło", e);
       }

       final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());


        Map<String, Object> claims = new HashMap<>();


        Optional<UserData> optionalUserByUsername = userService.getUserByUsername(userDetails.getUsername());
        if (optionalUserByUsername.isPresent())
        {
            Person personByUsername = optionalUserByUsername.get().getPerson();
            claims.put("loggedUserId", personByUsername.getId_person());
        } else {
            claims.put("loggedUserId", 0);
        }

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("GOD"))){
           claims.put("grantedAuthority", "GOD");
       } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
           claims.put("grantedAuthority", "ADMIN");
       } else {
           claims.put("grantedAuthority", "USER");
       }

       final String jwt = jwtUtil.generateToken(userDetails, claims);

       return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


}
