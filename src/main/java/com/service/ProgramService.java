package com.service;


import com.entities.ProgramEntity;
import com.mapper.ProgramMapper;
import com.model.Program;
import com.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

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

    @Transactional
    public void deleteProgram(int id) {

        ProgramEntity existingProgram = findById(id);
        programRepository.delete(existingProgram);
    }

    private ProgramEntity findById(int id) {
        return programRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Program not found!"));
    }
}
