package com.mapper;

import com.entities.AuthorEntity;
import com.entities.PaperEntity;
import com.model.PaperInput;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class PaperMapper {

    public static PaperEntity paperToEntity(PaperInput paperInput) {

        return PaperEntity.builder()
                .title(paperInput.getTitle())
                .content(paperInput.getContent())
                .topics(paperInput.getTopics())
                .keywords(paperInput.getKeywords())
                .build();
    }

    public static PaperInput entityToPaper(PaperEntity entity) {

        return PaperInput.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .authors(entity.getAuthors()!=null ? entity.getAuthors().stream().map(AuthorEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
//                .bidders(entity.getBids().values().stream().map(CommitteeMemberEntity::getEmail).collect(Collectors.toList()))
                .reviewers(entity.getReviews().stream().map(input -> input.getReviewer().getEmail()).collect(Collectors.toList()))

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
