package com.repository;

import com.entities.PaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaperRepository extends JpaRepository<PaperEntity, Integer> {
}
