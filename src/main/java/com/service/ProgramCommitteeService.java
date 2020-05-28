package com.service;

import com.entities.*;
//import com.mapper.EvaluationMapper;
import com.mapper.EvaluationMapper;
import com.mapper.PaperMapper;
import com.mapper.RecommendationMapper;
import com.mapper.UserMapper;
import com.model.*;
import com.output.EvaluationJSON;
import com.output.PaperJSON;
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
    public ProgramCommitteeService(RecommendationRepository recommendationRepository, PaperRepository paperRepository, UserRepository userRepository, SectionRepository sectionRepository, EvaluationRepository evaluationRepository, PCMemberRepository pcMemberRepository) {
        this.recommendationRepository = recommendationRepository;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.evaluationRepository = evaluationRepository;
        this.pcMemberRepository = pcMemberRepository;
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

    @Transactional
    public PaperJSON setPaperSection(int paperId, int sectionId) {
        PaperEntity existingPaper = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found"));
        existingPaper.setSection(sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + sectionId + " not found.")));

        paperRepository.save(existingPaper);
        return PaperMapper.entityToPaper(existingPaper);
    }

    @Transactional
    public EvaluationJSON reviewPaper(int paperId, String email, EvaluationInput evaluationInput) {

        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        CommitteeMemberEntity pcMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "PC member " + email + " not found"));

        RecommendationEntity recommendationEntity = new RecommendationEntity(evaluationInput.getRecommendation());
        recommendationRepository.save(recommendationEntity);

        EvaluationEntity review = pcMemberEntity.addReview(paperEntity, evaluationInput.getQualifier(), recommendationEntity);

        return EvaluationMapper.entityToEvaluation(review);
    }


}
