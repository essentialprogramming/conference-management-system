package com.service;


import com.entities.ProposalEntity;
import com.entities.UserEntity;
import com.mapper.UserMapper;
import com.model.User;
import com.repository.ProposalRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User addUser(User user) {
        UserEntity entity = UserMapper.userToEntity(user);
        userRepository.save(entity);

        return UserMapper.entityToUser(entity);
    }
}
