package com.service;

import com.entities.PaperEntity;
import com.entities.RecommendationEntity;
import com.entities.UserEntity;
import com.mapper.RecommendationMapper;
import com.mapper.UserMapper;
import com.model.Recommendation;
import com.model.Role;
import com.model.User;
import com.repository.PaperRepository;
import com.repository.RecommendationRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;


@Service
public class ProgramCommitteeService {

    private RecommendationRepository recommendationRepository;
    private PaperRepository paperRepository;
    private UserRepository userRepository;

    @Autowired
    public ProgramCommitteeService(RecommendationRepository recommendationRepository, PaperRepository paperRepository, UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Recommendation addRecommendation(Recommendation recommendation) {

        Optional<PaperEntity> paperEntity = paperRepository.findById(recommendation.getPaperId());

        RecommendationEntity recommendationEntity = RecommendationMapper.recommendationToEntity(recommendation);
        recommendationEntity.setPaper(paperEntity.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper not found.")));

        return RecommendationMapper.entityToRecommendation(recommendationRepository.save(recommendationEntity));
    }

    @Transactional
    public void updateUserRole(String email, Role role) {
        UserEntity existingUser = findUserEntityById(email);
        existingUser.setRole(role);

        userRepository.save(existingUser);
    }

    public UserEntity findUserEntityById(String email) {
        return userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
    }

    @Transactional
    public User findUserByEmail(String email) {
        UserEntity entity = findUserEntityById(email);
        return UserMapper.entityToUserWithSectionAndRole(entity);
    }
}
