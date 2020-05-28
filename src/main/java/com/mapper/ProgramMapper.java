package com.mapper;

import com.entities.ProgramEntity;
import com.model.ProgramInput;
import com.output.ProgramJSON;

public class ProgramMapper {

    public static ProgramEntity programToEntity(ProgramInput programInput){
        return ProgramEntity.builder()
                .date(programInput.getDate())
                .interval(programInput.getInterval())
                .abstractDeadline(programInput.getAbstractDeadline())
                .proposalDeadline(programInput.getProposalDeadline())
                .biddingDeadline(programInput.getBiddingDeadline())
                .build();
    }

    public static ProgramJSON entityToProgram(ProgramEntity entity){
        return ProgramJSON.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .interval(entity.getInterval())
                .abstractDeadline(entity.getAbstractDeadline())
                .proposalDeadline(entity.getProposalDeadline())
                .biddingDeadline(entity.getBiddingDeadline())
                .build();
    }

    public static ProgramEntity updateProgram(ProgramEntity entity, ProgramInput programInput){
        entity.setDate(programInput.getDate());
        entity.setInterval(programInput.getInterval());
        entity.setAbstractDeadline(programInput.getAbstractDeadline());
        entity.setProposalDeadline(programInput.getProposalDeadline());
        entity.setBiddingDeadline(programInput.getBiddingDeadline());
        return entity;
    }
}
