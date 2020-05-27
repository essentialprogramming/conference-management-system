package com.repository;


import com.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConferenceRepository extends JpaRepository<EventEntity, Integer> {
}
