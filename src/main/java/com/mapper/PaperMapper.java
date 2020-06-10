package com.mapper;

import com.entities.AuthorEntity;
import com.entities.CommitteeMemberEntity;
import com.entities.EvaluationEntity;
import com.entities.PaperEntity;
import com.model.PaperInput;
import com.model.Qualifier;
import com.output.PaperJSON;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class PaperMapper {

    public static PaperEntity paperToEntity(PaperInput paperInput) {

        return PaperEntity.builder()
                .title(paperInput.getTitle())
                .description(paperInput.getDescription())
                .fileName(paperInput.getFileName())
                .topics(paperInput.getTopics())
                .keywords(paperInput.getKeywords())
                .topics(paperInput.getTopics())
                .build();
    }

    public static PaperJSON entityToPaper(PaperEntity entity) {

        return PaperJSON.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .fileName(entity.getFileName())
                .authors(entity.getAuthors() != null ? entity.getAuthors().stream().map(AuthorEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
                .bidders(entity.getBids() != null ? entity.getBids().stream().map(input->input.getBidder().getEmail()).collect(Collectors.toList()) : new ArrayList<>())
                .reviewers(entity.getReviews() != null ? entity.getReviews().values().stream().map(CommitteeMemberEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
                .qualifiers(entity.getReviews() != null ? entity.getReviews().keySet().stream().map(EvaluationEntity::getQualifier).collect(Collectors.toList()) : new ArrayList<>())
                .topics(entity.getTopics())
                .keywords(entity.getKeywords())
                .build();
    }
}
