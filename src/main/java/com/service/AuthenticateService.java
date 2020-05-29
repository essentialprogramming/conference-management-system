package com.service;

import com.entities.ProfileEntity;
import com.mapper.ProfileMapper;
import com.model.ProfileInput;
import com.model.Role;
import com.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class AuthenticateService {

    private ProfileRepository profileRepository;
    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticateService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public void register(ProfileInput input) {

        Optional<ProfileEntity> profile = profileRepository.findById(input.getUsername());

        if (!profile.isPresent()) {
            ProfileEntity profileEntity = ProfileMapper.profileToEntity(input);

            profileEntity.setRoles(new Role[1]);
            profileEntity.getRoles()[0] = Role.valueOf(input.getRoles());
            profileEntity.setPassword(bCryptPasswordEncoder.encode(profileEntity.getPassword()));
            profileRepository.save(profileEntity);

        } else {
            if (profile.get().getRoles() == null) {
                profile.get().setRoles(new Role[1]);
                profile.get().getRoles()[0] = Role.valueOf(input.getRoles());
            } else {
                Role[] roles = new Role[profile.get().getRoles().length + 1];

                System.arraycopy(profile.get().getRoles(), 0, roles, 0, profile.get().getRoles().length);
                roles[profile.get().getRoles().length] = Role.valueOf(input.getRoles());
                profile.get().setRoles(roles);
            }

            profileRepository.save(profile.get());
        }
    }

    public ProfileEntity login(ProfileInput profileInput) {

        return profileRepository.findById(profileInput.getUsername()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Profile not found."));
    }
}
