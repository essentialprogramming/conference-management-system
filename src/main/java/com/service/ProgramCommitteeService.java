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

    private RecommendationRepository recommendationRepository;
    private PaperRepository paperRepository;
    private UserRepository userRepository;
    private SectionRepository sectionRepository;
    private EvaluationRepository evaluationRepository;
    private PCMemberRepository pcMemberRepository;
    private BidRepository bidRepository;

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

        List<CommitteeMemberEntity> pcMemberList = new ArrayList<>(paperEntity.getBids().values());
        if (pcMemberList.size() == 0){
            BidEntity bidEntity = new BidEntity();
            bidEntity.setStatus(status);
            bidRepository.save(bidEntity);

            paperEntity.getBids().put(bidEntity, pcMemberEntity);
            pcMemberEntity.getPapers().values().forEach(paper -> System.out.println(paper.getTitle()));
            paperRepository.save(paperEntity);
            return true;
        }
        for (CommitteeMemberEntity pcMember : pcMemberList)
            if (!(pcMember.getEmail().equals(pcMemberEntity.getEmail()))) {
                BidEntity bidEntity = new BidEntity();
                bidEntity.setStatus(status);
                bidRepository.save(bidEntity);

                paperEntity.getBids().put(bidEntity, pcMemberEntity);
                pcMemberEntity.getPapers().values().forEach(paper -> System.out.println(paper.getTitle()));
                paperRepository.save(paperEntity);
                return true;
            }
        return false;
    }


    @Transactional
    public String assignPaper(int paperId, String email) {
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        CommitteeMemberEntity committeeMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Program committee with email " + email + " not found!"));

        if (paperEntity.getReviews().size() < 4) {

            committeeMemberEntity.addReview(paperEntity);
            return "You are allowed to review this paper.";
        } else return "You are not allowed to review this paper.";

    }


    @Transactional
    public EvaluationJSON reviewPaper(int paperId, String email, EvaluationInput evaluationInput) {

        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + paperId + " not found!"));
        CommitteeMemberEntity pcMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "PC member " + email + " not found"));

        RecommendationEntity recommendationEntity = new RecommendationEntity(evaluationInput.getRecommendation());
        recommendationRepository.save(recommendationEntity);

        EvaluationJSON result = new EvaluationJSON();

        for (EvaluationEntity evaluation : paperEntity.getReviews()) {
            if (evaluation.getReviewer().equals(pcMemberEntity)) {
                evaluation.setQualifier(evaluationInput.getQualifier());
                evaluation.setRecommendation(recommendationEntity);
                result = EvaluationMapper.entityToEvaluation(evaluation);
            }
        }

        return result;
    }

    @Transactional
    public List<PaperJSON> getPapersOfReviewer(String email) {
        Optional<CommitteeMemberEntity> reviewer = pcMemberRepository.findById(email);
        List<EvaluationEntity> evalList = evaluationRepository.findAll();
        List<PaperEntity> paperList = new ArrayList<>();
        if (reviewer.isPresent()) {
            for (EvaluationEntity evaluation : evalList) {
                if (evaluation.getReviewer().getEmail().equals(reviewer.get().getEmail()))
                    paperList.add(evaluation.getPaper());
            }
        }

        return paperList.stream().map(PaperMapper::entityToPaper).collect(Collectors.toList());
    }

    @Transactional
    public List<ProgramCommitteeJSON> getProgramCommitteeMembersForPaper(int paperId) {
        List<CommitteeMemberEntity> pcMembers = pcMemberRepository.findAll();
        List<ProgramCommitteeJSON> programCommitteeJsons = new ArrayList<>();
        PaperEntity paper = paperRepository.findById(paperId).orElseThrow(() -> new RuntimeException("Paper does not exist!"));
        List<String> emailForMembersWithBids = new ArrayList<>();
        paper.getBids().forEach((bid, member) ->
        {
            programCommitteeJsons.add(new ProgramCommitteeJSON(member.getEmail(), bid.getStatus().getValue()));
            emailForMembersWithBids.add(member.getEmail());
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
    public List<User> getAllProgramCommitteeMembers()
    {
        return pcMemberRepository.findAll().stream().map(member ->
        {
            User memberJson = UserMapper.entityToUser(member);
            return memberJson;
        }).collect(Collectors.toList());
    }
}
