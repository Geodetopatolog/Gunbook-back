package pl.portalstrzelecki.psback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.portalstrzelecki.psback.domain.authentication.UserData;
import pl.portalstrzelecki.psback.repositories.UserRepository;
import pl.portalstrzelecki.psback.services.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

final UserRepository userRepository;

    @Override
    public void saveUser(UserData userData) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userData.setEncryptedPassword(passwordEncoder.encode(userData.getPassword()));
        userRepository.save(userData);
    }

    @Override
    public boolean deleteUser(String username) {
        Optional<UserData> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            UserData userData = optionalUser.get();

            userRepository.delete(userData);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateUser(UserData userData) {
        Optional<UserData> optionalUser = userRepository.findByUsername(userData.getUsername());
        if(optionalUser.isPresent()) {
            userRepository.save(userData);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Optional<UserData> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
