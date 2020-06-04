package com.service;

import com.entities.ApplicationUser;
import com.entities.RoleEntity;
import com.mapper.ProfileMapper;
import com.model.Authentication;
import com.model.Role;
import com.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticationService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Transactional
    public void register(Authentication input) {

        Optional<ApplicationUser> profile = applicationUserRepository.findById(input.getUserName());

        if (!profile.isPresent()) {
            ApplicationUser profileEntity = ProfileMapper.profileToEntity(input);


            if(profileEntity.getRoles()!=null)
            {
                profileEntity.getRoles().add(new RoleEntity(input.getRole()));
            }
            else
            {
                List<RoleEntity> newRoles = new ArrayList<>();
                newRoles.add(new RoleEntity(input.getRole()));
                profileEntity.setRoles(newRoles);
            }


            profileEntity.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));
            applicationUserRepository.save(profileEntity);

        } else {

            List<RoleEntity> roles = profile.get().getRoles();
            if(!roles.contains(new RoleEntity(input.getRole())))
            {

                profile.get().getRoles().add(new RoleEntity(input.getRole()));
            }

            applicationUserRepository.save(profile.get());
        }


    }


    public Optional<ApplicationUser> login(Authentication profileInput) {

        return applicationUserRepository.findById(profileInput.getUserName());
    }
}
