package pl.portalstrzelecki.psback.domain.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwt;

    private Long loggedUserId;
    private String role;
    private List<Long> loggedUserClubsIds;
    private List<Long> loggedUserOwnedClubsIds;
    private List<Long> loggedUserJoinedEventsIds;
    private List<Long> loggedUserAppliedClubsIds;
    private List<Long> loggedUserAppliedEventsIds;

}
