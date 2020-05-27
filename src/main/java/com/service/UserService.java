package com.service;


import com.entities.*;
import com.model.Status;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserService {

    private UserRepository userRepository;
    private SectionRepository sectionRepository;
    private PaperRepository paperRepository;
    private PCMemberRepository pcMemberRepository;
    private BidRepository bidRepository;

    @Autowired
    public UserService(UserRepository userRepository, SectionRepository sectionRepository, PaperRepository paperRepository, PCMemberRepository pcMemberRepository, BidRepository bidRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.paperRepository = paperRepository;
        this.pcMemberRepository = pcMemberRepository;
        this.bidRepository = bidRepository;
    }


    @Transactional
    public void registerToSection(String email, int sectionId) {

        UserEntity existingUser = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
        SectionEntity section = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section not found!"));

        existingUser.setSection(section);
        userRepository.save(existingUser);
    }



    @Transactional
    public void bidProposal(int proposalId, String email, Status status) {  // -> only if user is not already an author or reviewer of this paper
        PaperEntity paperEntity = paperRepository.findById(proposalId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + proposalId + " not found!"));
        CommitteeMemberEntity pcMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "PC member " + email + " not found"));

        BidEntity bidEntity = new BidEntity();
        bidEntity.setStatus(status);

        paperEntity.getBids().put(bidEntity, pcMemberEntity);
        pcMemberEntity.getPapers().values().forEach(paper -> System.out.println(paper.getTitle()));

        bidRepository.save(bidEntity);
        paperRepository.save(paperEntity);
    }

}
