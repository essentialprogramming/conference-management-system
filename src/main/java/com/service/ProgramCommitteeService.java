package com.service;

import com.entities.*;
//import com.mapper.EvaluationMapper;
import com.mapper.EvaluationMapper;
import com.mapper.PaperMapper;
import com.mapper.RecommendationMapper;
import com.mapper.UserMapper;
import com.model.*;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;


@Service
public class ProgramCommitteeService {

    private RecommendationRepository recommendationRepository;
    private PaperRepository paperRepository;
    private UserRepository userRepository;
    private SectionRepository sectionRepository;
    private EvaluationRepository evaluationRepository;
    private PCMemberRepository pcMemberRepository;

    @Autowired
    public ProgramCommitteeService(RecommendationRepository recommendationRepository, PaperRepository paperRepository, UserRepository userRepository, SectionRepository sectionRepository, EvaluationRepository evaluationRepository) {
        this.recommendationRepository = recommendationRepository;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.evaluationRepository = evaluationRepository;
    }

//    @Transactional
//    public Recommendation addRecommendation(Recommendation recommendation) {
//
//        Optional<PaperEntity> paperEntity = paperRepository.findById(recommendation.getPaperId());
//
//        RecommendationEntity recommendationEntity = RecommendationMapper.recommendationToEntity(recommendation);
//        recommendationEntity.setPaper(paperEntity.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper not found.")));
//
//        return RecommendationMapper.entityToRecommendation(recommendationRepository.save(recommendationEntity));
//    }


    @Transactional
    public User findUserByEmail(String email) {
        UserEntity entity = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
        return UserMapper.entityToUser(entity);
    }

//    @Transactional
//    public String assignPaper(int paperId, String email) {
//        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
//        UserEntity userEntity = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
//
//        long reviewers = paperEntity.getUsers().stream()
//                .filter(user -> user.getType().equals("reviewer"))
//                .count();
//
//        if (reviewers < 4) {
//            paperEntity.addUser(userEntity, "reviewer");
//            return "You are allowed to review this paper.";
//        } else return "You are not allowed to review this paper.";
//
//    }

//    @Transactional
//    public String reviewPaper(int paperId, String email, Qualifier qualifier, String recommendation) {
//        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
//        UserEntity userEntity = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
//
//        List<UserEntity> reviewers = paperEntity.getUsers().stream().filter(user->user.getType().equals("reviewer")).map(UserPaper::getUser).collect(Collectors.toList());
//
//        if (reviewers.contains(userEntity)) {
//
//            if (paperEntity.getQualifiers() == null) {
//                paperEntity.setQualifiers(new Qualifier[1]);
//                paperEntity.getQualifiers()[0] = qualifier;
//            } else {
//                Qualifier[] qualifiers = new Qualifier[paperEntity.getQualifiers().length + 1];
//
//                System.arraycopy(paperEntity.getQualifiers(), 0, qualifiers, 0, paperEntity.getQualifiers().length);
//                qualifiers[paperEntity.getQualifiers().length] = qualifier;
//                paperEntity.setQualifiers(qualifiers);
//            }
//
//            Recommendation recommendation1 = new Recommendation(recommendation, email, paperId);
//            addRecommendation(recommendation1);
//
//            return "Your review is added to paper.";
//
//        } else {
//            String link = "<a href=\"http://localhost:8080/api/user/bid/\">here</a>";
//            String here = link.substring(9, 43) + "/" + paperId + "/" + email;
//
//            return "You are not a reviewer of this paper. Ask " + here + " for permission to review this paper.";
//        }
//    }

//    @Transactional
//    public void updateSectionSupervisor(int sectionId, String email) {
//        SectionEntity sectionEntity = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section wit id " + sectionId + " not found!"));
//        UserEntity userEntity = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
//
//        sectionEntity.setSupervisor(userEntity);
//        sectionRepository.save(sectionEntity);
//    }

    @Transactional
    public Paper setPaperSection(int paperId, int sectionId) {
        PaperEntity existingPaper = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found"));
        existingPaper.setSection(sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + sectionId + " not found.")));

        paperRepository.save(existingPaper);
        return PaperMapper.entityToPaper(existingPaper);
    }

    @Transactional
    public Evaluation reviewPaper(String email, Evaluation evaluation) {

        EvaluationEntity entity = EvaluationMapper.evaluationToEntity(evaluation);
        PaperEntity paperEntity = paperRepository.findById(evaluation.getPaperId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + evaluation.getPaperId() + " not found!"));
        CommitteeMemberEntity pcMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "PC member " + email + " not found"));
        RecommendationEntity recommendationEntity = RecommendationMapper.recommendationToEntity(new Recommendation(evaluation.getRecommendation()));
        recommendationRepository.save(recommendationEntity);

        if (paperEntity.getQualifiers() == null) {
            paperEntity.setQualifiers(new Qualifier[1]);
            paperEntity.getQualifiers()[0] = evaluation.getQualifier();
        } else {
            Qualifier[] qualifiers = new Qualifier[paperEntity.getQualifiers().length + 1];

            System.arraycopy(paperEntity.getQualifiers(), 0, qualifiers, 0, paperEntity.getQualifiers().length);
            qualifiers[paperEntity.getQualifiers().length] = evaluation.getQualifier();
            paperEntity.setQualifiers(qualifiers);
        }

        entity.setPaper(paperEntity);
        entity.setQualifier(evaluation.getQualifier());
        entity.setReviewer(pcMemberEntity);
        entity.setRecommendation(recommendationEntity);

        evaluationRepository.save(entity);

        return EvaluationMapper.entityToEvaluation(entity);
    }
}
