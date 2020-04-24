package com.service;


import com.entities.PaperEntity;
import com.entities.SectionEntity;
import com.entities.UserEntity;
import com.mapper.UserMapper;
import com.model.Role;
import com.model.User;
import com.repository.PaperRepository;
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
    private PaperRepository paperRepository;

    @Autowired
    public UserService(UserRepository userRepository, SectionRepository sectionRepository, PaperRepository paperRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.paperRepository = paperRepository;
    }

    @Transactional
    public User register(User user) {

        UserEntity entity = UserMapper.userToEntity(user);
        return UserMapper.entityToUser(userRepository.save(entity));
    }

    @Transactional
    public void registerToSection(String email, int sectionId) {

        UserEntity existingUser = findById(email);
        SectionEntity section = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section not found!"));

        existingUser.setParticipantsSection(section);
        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(String email) {
        UserEntity existingUser = findById(email);
//        existingUser.setParticipantsSection(null);
        userRepository.delete(existingUser);
    }

    public UserEntity findById(String email) {
        return userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
    }

    @Transactional
    public void bidProposal(int proposalId, String email) {  // -> only if user is not already an author or reviewer of this paper
        PaperEntity paperEntity = paperRepository.findById(proposalId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + proposalId + " not found!"));
        UserEntity user = findById(email);

        paperEntity.addUser(user,"bidder");
    }
}
