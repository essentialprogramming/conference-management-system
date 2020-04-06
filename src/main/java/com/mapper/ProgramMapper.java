package com.mapper;

import com.entities.ProgramEntity;
import com.model.Program;

public class ProgramMapper {

    public static ProgramEntity programToEntity(Program program){
        return ProgramEntity.builder()
                .date(program.getDate())
                .interval(program.getInterval())
                .abstractDeadline(program.getAbstractDeadline())
                .proposalDeadline(program.getProposalDeadline())
                .biddingDeadline(program.getBiddingDeadline())
                .build();
    }

    public static Program entityToProgram(ProgramEntity entity){
        return Program.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .interval(entity.getInterval())
                .abstractDeadline(entity.getAbstractDeadline())
                .proposalDeadline(entity.getProposalDeadline())
                .biddingDeadline(entity.getBiddingDeadline())
                .build();
    }
}
