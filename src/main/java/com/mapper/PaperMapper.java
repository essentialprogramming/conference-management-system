package com.mapper;

import com.entities.PCMemberEntity;
import com.entities.PaperEntity;
import com.model.Paper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


public class PaperMapper {

    public static PaperEntity paperToEntity(Paper paper) {

        return PaperEntity.builder()
                .title(paper.getTitle())
                .content(paper.getContent())
//                .qualifiers(paper.getQualifiers())
                .topics(paper.getTopics())
                .keywords(paper.getKeywords())
                .bidders(new HashMap<>())
                .build();
    }

    public static Paper entityToPaper(PaperEntity entity) {

        return Paper.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .bidders(entity.getBids().values().stream().map(PCMemberEntity::getEmail).collect(Collectors.toList()))
                //.reviewers(entity.getReviewers().values().stream().map(PCMemberEntity::getEmail).collect(Collectors.toList()))

//                .qualifiers(entity.getQualifiers())
//                .authors(entity.getUsers() != null ? entity.getUsers().stream()
//                        .filter(userPaperEntity -> userPaperEntity.getType().equals("author"))
//                        .map(user -> user.getUser().getEmail())
//                        .collect(Collectors.toList()) : null)
//                .reviewers(entity.getUsers() != null ? entity.getUsers().stream()
//                        .filter(userPaperEntity -> userPaperEntity.getType().equals("reviewer"))
//                        .map(userPaperEntity -> userPaperEntity.getUser().getEmail())
//                        .collect(Collectors.toList()) : null)
//                .bidders(entity.getUsers() != null ? entity.getUsers().stream()
//                        .filter(userPaperEntity -> userPaperEntity.getType().equals("bidder"))
//                        .map(userPaperEntity -> userPaperEntity.getUser().getEmail())
//                        .collect(Collectors.toList()) : null)
                .topics(entity.getTopics())
                .keywords(entity.getKeywords())
                .sectionId(entity.getSection() != null ? entity.getSection().getId() : 0)
                .build();
    }
}
