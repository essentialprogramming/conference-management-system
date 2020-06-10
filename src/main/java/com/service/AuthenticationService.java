package com.service;

import com.entities.ApplicationUser;
import com.mapper.ProfileMapper;
import com.model.Authentication;
import com.model.Role;
import com.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationService {

    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticationService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Transactional
    public void register(Authentication input) {

        Optional<ApplicationUser> profile = applicationUserRepository.findById(input.getUserName());

        if (!profile.isPresent()) {
            ApplicationUser profileEntity = ProfileMapper.profileToEntity(input);

            profileEntity.setRoles(new Role[1]);
            profileEntity.getRoles()[0] = input.getRole();
            profileEntity.setPassword(bCryptPasswordEncoder.encode(profileEntity.getPassword()));
            applicationUserRepository.save(profileEntity);

        } else {
            if (profile.get().getRoles() == null) {
                profile.get().setRoles(new Role[1]);
                profile.get().getRoles()[0] = input.getRole();
            } else {
                Role[] roles = new Role[profile.get().getRoles().length + 1];

                System.arraycopy(profile.get().getRoles(), 0, roles, 0, profile.get().getRoles().length);
                roles[profile.get().getRoles().length] = input.getRole();
                profile.get().setRoles(roles);
            }

            applicationUserRepository.save(profile.get());
        }

    }


    public Optional<ApplicationUser> login(Authentication profileInput) {

        return applicationUserRepository.findById(profileInput.getUserName());
    }
}
