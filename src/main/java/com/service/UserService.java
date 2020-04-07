package com.service;


import com.entities.SectionEntity;
import com.entities.UserEntity;
import com.mapper.UserMapper;
import com.model.Role;
import com.model.User;
import com.repository.SectionRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private SectionRepository sectionRepository;

    @Autowired
    public UserService(UserRepository userRepository, SectionRepository sectionRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public User addUser(User user) {

        UserEntity entity = UserMapper.userToEntity(user);
        return UserMapper.entityToUser(userRepository.save(entity));
    }

    @Transactional
    public void updateSection(String email, int sectionId) {

        UserEntity existingUser = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
        SectionEntity section = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section not found!"));

        existingUser.setParticipantsSection(section);
        userRepository.save(existingUser);
    }

    @Transactional
    public void updateRole(String email, Role role) {
        UserEntity existingUser = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
        existingUser.setRole(role);

        userRepository.save(existingUser);
    }

    @Transactional
    public User findByEmail(String email) {
        UserEntity entity = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
        return UserMapper.entityToUserWithSectionAndRole(entity);
    }
}
