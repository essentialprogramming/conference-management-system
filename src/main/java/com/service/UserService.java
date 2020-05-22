package com.service;


import com.entities.*;
import com.mapper.UserMapper;
import com.model.Status;
import com.model.User;
import com.repository.*;
import org.hibernate.engine.transaction.jta.platform.internal.BitronixJtaPlatform;
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
    public User register(User user) {

        UserEntity entity = UserMapper.userToEntity(user);
        return UserMapper.entityToUser(userRepository.save(entity));
    }

    @Transactional
    public void registerToSection(String email, int sectionId) {

        UserEntity existingUser = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
        SectionEntity section = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section not found!"));

        existingUser.setParticipantsSection(section);
        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(String email) {
        UserEntity existingUser = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
//        existingUser.setParticipantsSection(null);
        userRepository.delete(existingUser);
    }

    @Transactional
    public User registerAsPcMember(User user) {

        PCMemberEntity entity = new PCMemberEntity(user.getEmail());
        pcMemberRepository.save(entity);

        return UserMapper.entityToUser(entity);
    }

    @Transactional
    public void bidProposal(int proposalId, String email, Status status) {  // -> only if user is not already an author or reviewer of this paper
        PaperEntity paperEntity = paperRepository.findById(proposalId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + proposalId + " not found!"));
        PCMemberEntity pcMemberEntity = pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "PC member " + email + " not found"));

        BidEntity bidEntity = new BidEntity();
        bidEntity.setStatus(status);

        paperEntity.getBids().put(bidEntity, pcMemberEntity);
        pcMemberEntity.getPapers().values().forEach(paper -> System.out.println(paper.getTitle()));

        bidRepository.save(bidEntity);
        paperRepository.save(paperEntity);
    }

}
