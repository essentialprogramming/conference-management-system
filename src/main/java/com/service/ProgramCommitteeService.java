package com.service;

import com.entities.PaperEntity;
import com.entities.RecommendationEntity;
import com.entities.UserEntity;
import com.mapper.RecommendationMapper;
import com.mapper.UserMapper;
import com.model.Qualifier;
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

import java.net.MalformedURLException;
import java.net.URL;
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

    @Transactional
    public void assignPaper(int paperId, String email) {
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        UserEntity userEntity = findUserEntityById(email);
        paperEntity.getReviewers().add(userEntity);

        paperRepository.save(paperEntity);
    }

    @Transactional
    public String reviewPaper(int paperId, String email, Qualifier qualifier, String recommendation) {
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        UserEntity userEntity = findUserEntityById(email);

        if (paperEntity.getReviewers().contains(userEntity)) {

            if (paperEntity.getQualifiers() == null) {
                paperEntity.setQualifiers(new Qualifier[1]);
                paperEntity.getQualifiers()[0] = qualifier;
            } else {
                Qualifier[] qualifiers = new Qualifier[paperEntity.getQualifiers().length + 1];

                System.arraycopy(paperEntity.getQualifiers(), 0, qualifiers, 0, paperEntity.getQualifiers().length);
                qualifiers[paperEntity.getQualifiers().length] = qualifier;
                paperEntity.setQualifiers(qualifiers);
            }

            Recommendation recommendation1 = new Recommendation(recommendation, email, paperId);
            addRecommendation(recommendation1);

            return "Your review is added to paper.";

        } else {
//            try {
//                URL url = new URL("http://localhost:8080/api/user/bid/" +paperId +"/"+email);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
            return "You are not a reviewer of this paper. Ask here for permission to review the paper.";
        }

    }
}
