package pl.portalstrzelecki.psback.domain.Authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwt;



}
