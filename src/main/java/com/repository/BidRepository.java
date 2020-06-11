package com.repository;


import com.entities.BidEntity;
import com.entities.CommitteeMemberEntity;
import com.entities.EvaluationEntity;
import com.entities.PaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<BidEntity,Integer> {

    Optional<BidEntity> findByPaperAndBidder(PaperEntity paper, CommitteeMemberEntity committeeMember);
}
