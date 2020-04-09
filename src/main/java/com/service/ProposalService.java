package com.service;


import com.entities.PaperEntity;
import com.mapper.PaperMapper;
import com.model.Paper;
import com.repository.PaperRepository;
import com.repository.UserRepository;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProposalService {

    private PaperRepository paperRepository;
    private UserRepository userRepository;

    @Autowired
    public ProposalService(PaperRepository paperRepository, UserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Paper submitPaper(Paper paper) {

        PaperEntity entity = PaperMapper.paperToEntity(paper);
        entity.setAuthors(userRepository.findAllById(paper.getAuthors()));
//        entity.setReviewers(userRepository.findAllById(proposal.getReviewers()));

        return PaperMapper.entityToPaper(paperRepository.save(entity));

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
