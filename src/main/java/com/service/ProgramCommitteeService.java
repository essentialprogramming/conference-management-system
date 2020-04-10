package com.service;

import com.entities.PaperEntity;
import com.entities.RecommendationEntity;
import com.mapper.RecommendationMapper;
import com.model.Recommendation;
import com.repository.PaperRepository;
import com.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;


@Service
public class ProgramCommitteeService {

    private RecommendationRepository recommendationRepository;
    private PaperRepository paperRepository;

    @Autowired
    public ProgramCommitteeService(RecommendationRepository recommendationRepository, PaperRepository paperRepository) {
        this.recommendationRepository = recommendationRepository;
        this.paperRepository = paperRepository;
    }

    @Transactional
    public Recommendation addRecommendation(Recommendation recommendation) {

        Optional<PaperEntity> paperEntity = paperRepository.findById(recommendation.getPaperId());

        RecommendationEntity recommendationEntity = RecommendationMapper.recommendationToEntity(recommendation);
        recommendationEntity.setPaper(paperEntity.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Paper not found.")));

        return RecommendationMapper.entityToRecommendation(recommendationRepository.save(recommendationEntity));
    }
}
