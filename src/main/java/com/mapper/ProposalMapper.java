package com.mapper;

import com.entities.ProposalEntity;
import com.model.Proposal;

public class ProposalMapper {

    public static ProposalEntity proposalToEntity(Proposal proposal) {

        return ProposalEntity.builder()
                .title(proposal.getTitle())
                .content(proposal.getContent())
//                .keywords(proposal.getKeywords())
                .build();
    }

    public static Proposal entityToProposal(ProposalEntity entity) {

        return Proposal.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
//                .keywords(entity.getKeywords())
                .build();
    }
}
