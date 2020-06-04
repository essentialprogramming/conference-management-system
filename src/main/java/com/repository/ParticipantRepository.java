package com.repository;

import com.entities.ParticipantEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends UserBaseRepository<ParticipantEntity> {
}
