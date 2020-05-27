package com.repository;

import com.entities.CommitteeMemberEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface PCMemberRepository extends UserBaseRepository<CommitteeMemberEntity> {
}
