package pl.portalstrzelecki.psback.component.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.Authentication.UserData;
import pl.portalstrzelecki.psback.services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

   final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserData> optionalUserData = userService.getUserByUsername(username);

        UserDetails userDetails;

        if (optionalUserData.isPresent()) {
            UserData userData = optionalUserData.get();

            Collection<GrantedAuthority> roles = new ArrayList<>();

            if (userData.isGod()) {
                roles.add(new SimpleGrantedAuthority("USER"));
                roles.add(new SimpleGrantedAuthority("ADMIN"));
                roles.add(new SimpleGrantedAuthority("GOD"));
            } else if (userData.isAdmin()) {
                roles.add(new SimpleGrantedAuthority("USER"));
                roles.add(new SimpleGrantedAuthority("ADMIN"));
            } else if (userData.isUser()) {
                roles.add(new SimpleGrantedAuthority("USER"));
            }

            userDetails = User.withUsername(userData.getUsername())
                .username(userData.getUsername())
                .password(userData.getPassword())
                .authorities(roles)
                .build();

            return userDetails;
        } else {
            throw new UsernameNotFoundException("Nie znaleziono kolesia :(");
        }

    }
}
