package com.mapper;

import com.entities.ProposalEntity;
import com.entities.UserEntity;
import com.model.Proposal;

import java.util.stream.Collectors;

public class ProposalMapper {

    public static ProposalEntity proposalToEntity(Proposal proposal) {

        return ProposalEntity.builder()
                .title(proposal.getTitle())
                .content(proposal.getContent())
                .qualifiers(proposal.getQualifiers())
                .topics(proposal.getTopics())
                .keywords(proposal.getKeywords())
                .build();
    }

    public static Proposal entityToProposal(ProposalEntity entity) {

        return Proposal.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .qualifiers(entity.getQualifiers())
                .authors(entity.getAuthors().stream().map(UserEntity::getEmail).collect(Collectors.toList()))
                .reviewers(entity.getReviewers().stream().map(UserEntity::getEmail).collect(Collectors.toList()))
                .topics(entity.getTopics())
                .keywords(entity.getKeywords())
                .build();
    }
}
