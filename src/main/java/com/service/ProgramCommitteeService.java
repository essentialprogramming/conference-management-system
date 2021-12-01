package com.service;

import com.entities.*;
import com.mapper.EvaluationMapper;
import com.model.EvaluationInput;
import com.mapper.PaperMapper;
import com.mapper.UserMapper;
import com.model.*;
import com.output.EvaluationJSON;
import com.output.PaperJSON;
import com.output.ProgramCommitteeJSON;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProgramCommitteeService {

    private final RecommendationRepository recommendationRepository;
    private final PaperRepository paperRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final EvaluationRepository evaluationRepository;
    private final PCMemberRepository pcMemberRepository;
    private final BidRepository bidRepository;

    @Autowired
    public ProgramCommitteeService(RecommendationRepository recommendationRepository, PaperRepository paperRepository, UserRepository userRepository, SectionRepository sectionRepository, EvaluationRepository evaluationRepository, PCMemberRepository pcmmemberRepository, BidRepository bidRepository) {
        this.recommendationRepository = recommendationRepository;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.evaluationRepository = evaluationRepository;
        this.pcMemberRepository = pcmmemberRepository;
        this.bidRepository = bidRepository;
    }


    @Transactional
    public PaperJSON assignPaperToSection(int paperId, int sectionId) {
        PaperEntity existingPaper = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found"));
        existingPaper.setSection(sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + sectionId + " not found.")));

        paperRepository.save(existingPaper);
        return PaperMapper.entityToPaper(existingPaper);
    }

    @Transactional
    public boolean bidProposal(int paperId, String email, Status status) {  // -> only if user is not already an author or reviewer of this paper
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        CommitteeMemberEntity pcMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "PC member " + email + " not found"));

        Optional<BidEntity> bidEntity = bidRepository.findByPaperAndBidder(paperEntity, pcMemberEntity);

        if (bidEntity.isPresent()) {
            bidEntity.get().setStatus(status);

        } else {

            BidEntity bid = new BidEntity(pcMemberEntity, paperEntity);
            bid.setStatus(status);
            pcMemberEntity.getBids().add(bid);
            paperEntity.getBids().add(bid);

            return true;
        }
        return false;
    }


    @Transactional
    public String assignPaper(int paperId, String email) {
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        CommitteeMemberEntity committeeMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Program committee with email " + email + " not found!"));

        Optional<EvaluationEntity> evaluation = evaluationRepository.findByPaperAndReviewer(paperEntity.getId(), committeeMemberEntity.getEmail());
        if (paperEntity.getReviews().size() < 4) {

            if (!evaluation.isPresent()) {
                EvaluationEntity review = new EvaluationEntity(committeeMemberEntity, paperEntity);
                evaluationRepository.save(review);
                committeeMemberEntity.getEvaluations().put(review, paperEntity);
                paperEntity.getReviews().put(review, committeeMemberEntity);
                paperRepository.save(paperEntity);
            }

            return "You are allowed to review this paper.";
        } else return "You are not allowed to review this paper.";

    }


    @Transactional
    public EvaluationJSON reviewPaper(int paperId, String email, EvaluationInput evaluationInput) {

        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        CommitteeMemberEntity pcMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "PC member " + email + " not found"));

        RecommendationEntity recommendationEntity = new RecommendationEntity(evaluationInput.getRecommendation());
        recommendationRepository.save(recommendationEntity);

        Optional<EvaluationEntity> evaluationEntity = evaluationRepository.findByPaperAndReviewer(paperEntity.getId(), pcMemberEntity.getEmail());

        evaluationEntity.get().setQualifier(evaluationInput.getQualifier());
        evaluationEntity.get().setRecommendation(recommendationEntity);

        return EvaluationMapper.entityToEvaluation(evaluationEntity.get());
    }

    @Transactional
    public List<PaperJSON> getPapersOfReviewer(String email) {
        Optional<CommitteeMemberEntity> reviewer = pcMemberRepository.findById(email);

        Collection<PaperEntity> paperList = new ArrayList<>();

        if (reviewer.isPresent()) {
            paperList = reviewer.get().getEvaluations().values();

        }

        return paperList.stream().map(PaperMapper::entityToPaper).collect(Collectors.toList());
    }

    @Transactional
    public List<ProgramCommitteeJSON> getProgramCommitteeMembersForPaper(int paperId) {
        List<CommitteeMemberEntity> pcMembers = pcMemberRepository.findAll();
        List<ProgramCommitteeJSON> programCommitteeJsons = new ArrayList<>();
        PaperEntity paper = paperRepository.findById(paperId).orElseThrow(() -> new RuntimeException("Paper does not exist!"));
        List<String> emailForMembersWithBids = new ArrayList<>();


        paper.getBids().forEach(bid -> {
            programCommitteeJsons.add(new ProgramCommitteeJSON(bid.getBidder().getEmail(), bid.getStatus().getValue()));
            emailForMembersWithBids.add(bid.getBidder().getEmail());
        });

        pcMembers.forEach(pcMember -> {
            if (!emailForMembersWithBids.contains(pcMember.getEmail())) {
                programCommitteeJsons.add(new ProgramCommitteeJSON(pcMember.getEmail(), "NO BID"));
            }
        });
        Collections.sort(programCommitteeJsons, new Comparator<ProgramCommitteeJSON>() {
            public int compare(ProgramCommitteeJSON p1, ProgramCommitteeJSON p2) {
                return p1.getStatus().compareTo(p2.getStatus());
            }
        });
        return programCommitteeJsons;
    }

    @Transactional
    public List<User> getAllProgramCommitteeMembers() {
        return pcMemberRepository.findAll().stream().map(member ->
        {
            User memberJson = UserMapper.entityToUser(member);
            return memberJson;
        }).collect(Collectors.toList());
    }
}
