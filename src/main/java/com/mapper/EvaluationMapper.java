package com.mapper;

import com.entities.EvaluationEntity;
import com.model.Evaluation;

public class EvaluationMapper {


    public static Evaluation entityToEvaluation(EvaluationEntity entity) {
        return Evaluation.builder()
                .qualifier(entity.getQualifier())
                .recommendation(entity.getRecommendation().getText())
                .reviewer(entity.getReviewer().getEmail())
                .paperId(entity.getPaper().getId())
                .build();
    }
}
