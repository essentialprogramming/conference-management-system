package com.mapper;

import com.entities.EvaluationEntity;
import com.model.EvaluationInput;
import com.output.EvaluationJSON;

public class EvaluationMapper {

    public static EvaluationEntity evaluationToEntity(Evaluation evaluation) {
        return EvaluationEntity.builder()
                .qualifier(evaluation.getQualifier())
                .build();
    }

    public static EvaluationJSON entityToEvaluation(EvaluationEntity entity) {
        return EvaluationJSON.builder()
                .id(entity.getId())
                .qualifier(entity.getQualifier())
                .recommendation(entity.getRecommendation().getText())
                .reviewer(entity.getReviewer().getEmail())
                .paperId(entity.getPaper().getId())
                .build();
    }
}
