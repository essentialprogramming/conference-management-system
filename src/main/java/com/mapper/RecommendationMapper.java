package com.mapper;

import com.entities.RecommendationEntity;
import com.model.Recommendation;

public class RecommendationMapper {

    public static RecommendationEntity recommendationToEntity(Recommendation recommendation) {
        return RecommendationEntity.builder()
                .text(recommendation.getText())
                .email(recommendation.getEmail())
                .build();
    }

    public static Recommendation entityToRecommendation(RecommendationEntity entity) {
        return Recommendation.builder()
                .id(entity.getId())
                .text(entity.getText())
                .email(entity.getEmail())
                .proposalId(entity.getProposal().getId())
                .build();
    }
}
