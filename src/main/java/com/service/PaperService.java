package com.service;


import com.entities.AuthorEntity;
import com.entities.PaperEntity;
import com.entities.UserEntity;
import com.mapper.PaperMapper;
import com.model.Paper;
import com.repository.AuthorRepository;
import com.repository.PaperRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PaperService {

    private PaperRepository paperRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public PaperService(PaperRepository paperRepository, AuthorRepository authorRepository) {
        this.paperRepository = paperRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Paper submitPaper(Paper paperInput) {

        PaperEntity entity = PaperMapper.paperToEntity(paperInput);
//        List<UserEntity> users = userRepository.findAllById(paper.getAuthors());
//
//        for (UserEntity user : users) {
//            entity.addUser(user, "author");
//        }

        for (String email : paperInput.getAuthors()) {

            AuthorEntity newAuthor = new AuthorEntity(email);
            authorRepository.save(newAuthor);
            entity.addAuthor(newAuthor);

        }

        paperRepository.save(entity);
        return PaperMapper.entityToPaper(entity);
    }

    @Transactional
    public Paper findById(int id) {

        PaperEntity entity = paperRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper with id " + id + " not found"));
        return PaperMapper.entityToPaper(entity);
    }

    @Transactional
    public List<Paper> getAll() {
        return paperRepository.findAll().stream().map(PaperMapper::entityToPaper).collect(Collectors.toList());
    }

    @Transactional
    public void updatePaper(int paperId, String newContent) {

        paperRepository.findById(paperId).ifPresent(paperEntity -> paperEntity.setContent(newContent));
    }

}
