package pl.portalstrzelecki.psback.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.portalstrzelecki.psback.domain.authentication.UserData;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserData, Long> {

    Optional<UserData> findByUsername(String username);
}
