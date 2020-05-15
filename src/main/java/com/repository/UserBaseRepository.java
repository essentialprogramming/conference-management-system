package com.repository;


import com.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBaseRepository<T extends UserEntity> extends JpaRepository<T,String> {
}
