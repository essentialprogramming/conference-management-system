package com.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_entity")
public class UserEntity {

    @Id
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "authors")
    private List<ProposalEntity> proposalEntities;

    @ManyToMany(mappedBy = "reviewers")
    private List<ProposalEntity> proposalEntitiesReviewers;

    @ManyToMany(mappedBy = "programCommittee")
    private List<EventEntity> events;

    @ManyToMany(mappedBy = "participants")
    private List<EventEntity> eventsParticipants;

    @ManyToMany(mappedBy = "speakers")
    private List<EventEntity> eventsSpeakers;
}
