package com.service;


import com.entities.SectionEntity;
import com.entities.UserEntity;
import com.mapper.UserMapper;
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

        Optional<SectionEntity> sectionEntity = sectionRepository.findById(user.getParticipantsSectionId());

        UserEntity entity = UserMapper.userToEntity(user);
        entity.setParticipantsSection(sectionEntity.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section not found!")));

        return UserMapper.entityToUser(userRepository.save(entity));
    }
}
