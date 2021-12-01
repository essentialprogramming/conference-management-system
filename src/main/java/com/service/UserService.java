package com.service;


import com.entities.*;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;


    @Autowired
    public UserService(UserRepository userRepository, SectionRepository sectionRepository, PaperRepository paperRepository, PCMemberRepository pcMemberRepository, BidRepository bidRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;

    }


    @Transactional
    public void registerToSection(String email, int sectionId) {

        UserEntity existingUser = userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email " + email + " not found!"));
        SectionEntity section = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section not found!"));

        existingUser.setSection(section);
        userRepository.save(existingUser);
    }





}
