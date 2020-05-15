package com.service;


import com.entities.PCMemberEntity;
import com.entities.PaperEntity;
import com.entities.SectionEntity;
import com.entities.UserEntity;
import com.mapper.UserMapper;
import com.model.User;
import com.repository.PCMemberRepository;
import com.repository.PaperRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
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

    @Autowired
    public UserService(UserRepository userRepository, SectionRepository sectionRepository, PaperRepository paperRepository, PCMemberRepository pcMemberRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.paperRepository = paperRepository;
        this.pcMemberRepository = pcMemberRepository;
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
    public void bidProposal(int proposalId, String email) {  // -> only if user is not already an author or reviewer of this paper
        PaperEntity paperEntity = paperRepository.findById(proposalId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + proposalId + " not found!"));
        UserEntity user = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));

        paperEntity.addUser(user, "bidder");
    }

    @Transactional
    public User registerAsPcMember(User user) {

        PCMemberEntity entity = new PCMemberEntity(user.getEmail());
        pcMemberRepository.save(entity);

        return UserMapper.entityToUser(entity);
    }
}
