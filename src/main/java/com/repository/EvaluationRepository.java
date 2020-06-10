package com.repository;

import com.entities.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Integer> {

    Optional<EvaluationEntity> findByPaperAndReviewer(int paperId, String reviewer);
}
