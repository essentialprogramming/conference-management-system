package com.mapper;

import com.entities.RecommendationEntity;
import com.model.RecommendationInput;

public class RecommendationMapper {

    public static RecommendationEntity recommendationToEntity(RecommendationInput recommendationInput) {
        return RecommendationEntity.builder()
                .text(recommendationInput.getText())
                .build();
    }

    public static RecommendationInput entityToRecommendation(RecommendationEntity entity) {
        return RecommendationInput.builder()
                .id(entity.getId())
                .text(entity.getText())
                .build();
    }
}
