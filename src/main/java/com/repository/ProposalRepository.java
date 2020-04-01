package com.repository;

import com.entities.ProposalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProposalRepository extends JpaRepository<ProposalEntity, Integer> {
}
