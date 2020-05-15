package com.repository;

import com.entities.PCMemberEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface PCMemberRepository extends UserBaseRepository<PCMemberEntity> {
}
