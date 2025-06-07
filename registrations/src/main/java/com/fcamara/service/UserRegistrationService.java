package com.fcamara.service;

import com.fcamara.dto.UserRegistrationRequestDTO;
import com.fcamara.model.Login;
import com.fcamara.model.Profile;
import com.fcamara.repository.LoginRepository;
import com.fcamara.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRegistrationService {

    private final LoginRepository loginRepository;
    private final ProfileRepository profileRepository;
    private final KeycloakService keycloakService;

    public UserRegistrationService(LoginRepository loginRepository,
                                   ProfileRepository profileRepository,
                                   KeycloakService keycloakService) {
        this.loginRepository = loginRepository;
        this.profileRepository = profileRepository;
        this.keycloakService = keycloakService;
    }

    public void registerUser(UserRegistrationRequestDTO dto) {
        if (loginRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Username já está em uso.");
        }

        String keycloakId = keycloakService.createUser(dto.username(), dto.username());

        Login login = new Login();
        login.setId(UUID.randomUUID());
        login.setUsername(dto.username());
        login.setKeycloakId(keycloakId);
        loginRepository.save(login);

        Profile profile = new Profile();
        profile.setId(UUID.randomUUID());
        profile.setPerson(UUID.fromString(dto.personId()));
        profile.setPosition(dto.position());
        profile.setContract(dto.contract());
        profile.setStartDate(dto.startDate());
        profile.setTermLgpd(dto.termLgpd());
        profile.setHasAdmin(dto.hasAdmin());
        profile.setHasMobile(dto.hasMobile());
        profile.setHasManager(dto.hasManager());
        profile.setActive(true);
        profileRepository.save(profile);
    }
}
