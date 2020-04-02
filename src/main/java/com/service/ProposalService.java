package com.service;


import com.entities.ProposalEntity;
import com.mapper.ProposalMapper;
import com.model.Proposal;
import com.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;


@Service
public class ProposalService {

    private ProposalRepository proposalRepository;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @Transactional
    public Proposal addProposal(Proposal proposal) {

        ProposalEntity entity = ProposalMapper.proposalToEntity(proposal);

        return ProposalMapper.entityToProposal(proposalRepository.save(entity));

    }

    @Transactional
    public Proposal findById(int id) {

        ProposalEntity entity = proposalRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Proposal with id " + id + " not found"));
        return ProposalMapper.entityToProposal(entity);
    }
}
