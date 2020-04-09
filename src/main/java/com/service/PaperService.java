package com.service;


import com.entities.PaperEntity;
import com.mapper.PaperMapper;
import com.model.Paper;
import com.repository.PaperRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;


@Service
public class PaperService {

    private PaperRepository paperRepository;
    private UserRepository userRepository;

    @Autowired
    public PaperService(PaperRepository paperRepository, UserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Paper addPaper(Paper paper) {

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
}
