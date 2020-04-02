package com.service;


import com.entities.ProposalEntity;
import com.entities.RecommendationEntity;
import com.mapper.RecommendationMapper;
import com.model.Recommendation;
import com.repository.ProposalRepository;
import com.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class RecommendationService {

    private RecommendationRepository recommendationRepository;
    private ProposalRepository proposalRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository, ProposalRepository proposalRepository) {
        this.recommendationRepository = recommendationRepository;
        this.proposalRepository = proposalRepository;
    }

    @Transactional
    public Recommendation addRecommendation(Recommendation recommendation) {

        Optional<ProposalEntity> proposalEntity = proposalRepository.findById(recommendation.getProposalId());

        RecommendationEntity entity = RecommendationMapper.recommendationToEntity(recommendation);
        entity.setProposal(proposalEntity.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Proposal not found.")));

        return RecommendationMapper.entityToRecommendation(recommendationRepository.save(entity));
    }
}
