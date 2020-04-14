package com.mapper;

import com.entities.PaperEntity;
import com.entities.UserEntity;
import com.model.Paper;

import java.util.stream.Collectors;

public class PaperMapper {

    public static PaperEntity paperToEntity(Paper paper) {

        return PaperEntity.builder()
                .title(paper.getTitle())
                .content(paper.getContent())
                .qualifiers(paper.getQualifiers())
                .topics(paper.getTopics())
                .keywords(paper.getKeywords())
                .build();
    }

    public static Paper entityToPaper(PaperEntity entity) {

        return Paper.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .qualifiers(entity.getQualifiers())
                .authors(entity.getAuthors().stream().map(UserEntity::getEmail).collect(Collectors.toList()))
                .reviewers(entity.getReviewers() != null ? entity.getReviewers().stream().map(UserEntity::getEmail).collect(Collectors.toList()) : null)
                .bidders(entity.getBidders() != null ? entity.getBidders().stream().map(UserEntity::getEmail).collect(Collectors.toList()) : null)
                .topics(entity.getTopics())
                .keywords(entity.getKeywords())
                .build();
    }
}
