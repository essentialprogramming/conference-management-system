package com.repository;

import com.entities.AuthorEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends UserBaseRepository<AuthorEntity> {
}
