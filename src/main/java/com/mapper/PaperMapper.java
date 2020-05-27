package com.mapper;

import com.entities.AuthorEntity;
import com.entities.CommitteeMemberEntity;
import com.entities.PaperEntity;
import com.model.Paper;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class PaperMapper {

    public static PaperEntity paperToEntity(Paper paper) {

        return PaperEntity.builder()
                .title(paper.getTitle())
                .content(paper.getContent())
                .topics(paper.getTopics())
                .keywords(paper.getKeywords())
                .build();
    }

    public static Paper entityToPaper(PaperEntity entity) {

        return Paper.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .authors(entity.getAuthors()!=null ? entity.getAuthors().stream().map(AuthorEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
//                .bidders(entity.getBids().values().stream().map(CommitteeMemberEntity::getEmail).collect(Collectors.toList()))
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
                .build();
    }
}
