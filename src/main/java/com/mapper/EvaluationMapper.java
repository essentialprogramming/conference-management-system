package com.mapper;

import com.entities.EvaluationEntity;
import com.output.EvaluationJSON;

public class EvaluationMapper {

    public static EvaluationJSON entityToEvaluation(EvaluationEntity entity) {
        return EvaluationJSON.builder()
                .qualifier(entity.getQualifier())
                .recommendation(entity.getRecommendation().getText())
                .reviewer(entity.getReviewer())
                .paperId(entity.getPaper())
                .build();
    }
}
