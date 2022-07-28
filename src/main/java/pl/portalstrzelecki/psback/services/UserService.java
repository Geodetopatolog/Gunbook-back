package pl.portalstrzelecki.psback.services;

import pl.portalstrzelecki.psback.domain.Authentication.UserData;

import java.util.Optional;

public interface UserService {

    public void saveUser(UserData userData);

    public boolean deleteUser(String username);

    public boolean updateUser(UserData userData);

    public Optional<UserData> getUserByUsername(String username);

//    public void encodePassword(UserData userData);


}
