package com.service;


import com.entities.ProgramEntity;
import com.mapper.ProgramMapper;
import com.model.Program;
import com.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgramService {

    private ProgramRepository programRepository;

    @Autowired
    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Transactional
    public Program addProgram(Program program) {
        ProgramEntity entity = ProgramMapper.programToEntity(program);
        return ProgramMapper.entityToProgram(programRepository.save(entity));
    }
}
